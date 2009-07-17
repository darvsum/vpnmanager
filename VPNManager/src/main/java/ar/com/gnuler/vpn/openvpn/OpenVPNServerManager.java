package ar.com.gnuler.vpn.openvpn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ar.com.gnuler.util.ManagedProcess;
import ar.com.gnuler.util.ManagedProcess.ProcessListener;
import ar.com.gnuler.vpn.openvpn.OpenVPNDHParamGenerator.DHParamGeneratorListener;

/**
 * @author matu
 *
 */
public class OpenVPNServerManager implements ProcessListener, DHParamGeneratorListener {
	
	// Posible OpenVPNServers status
	public enum OpenVPNServerStatus {RUNNING, STOPPED,STOPPING, GENERATING_DH} 
	
	// Singleton
	private static OpenVPNServerManager INSTANCE = null;
	
	// Contains the installed Servers
	private HashMap<String, OpenVPNServer> installedServers = new HashMap<String, OpenVPNServer>();
	
	// Contains the servers running processes
	private HashMap<String, ManagedProcess> serverProcesses = new HashMap<String, ManagedProcess>();
	
	// Contains the currecntly active DH generators
	private HashMap<String, OpenVPNDHParamGenerator> dhParamGenerators = new HashMap<String, OpenVPNDHParamGenerator>();
	
	// Contains the status of all the installed OpenVPN Servers
	private HashMap<String, OpenVPNServerStatus> serversStatus = new HashMap<String, OpenVPNServerStatus>();
	
	//TODO remplazar por un archivo de property's
	
	private static final String BASE_INSTANCE_DIR = "/tmp/openvpn/instances";
	private static final String BASE_LOG_DIR = "/tmp/openvpn/log";
	private static final String BASE_RUN_DIR = "/tmp/openvpn/run";
	private static final String CONF_FILE_NAME = "server.conf";
	private static final String OPENVPN_FILE = "/usr/sbin/openvpn";
	private static final String CONFIG_SWITCH = "--config";
	private static final String DHPARAM_FILE_NAME = "dh1024.pem";
	private static final String LOG_FILE_EXTENSION = ".log";
	
	public OpenVPNServerManager(){
		
	}
	
	
	/**
	 * Returns the complete path of the supplied server log  file
	 * 
	 * @param server Server from who you want to know the log file path
	 * @return Complete log file path
	 */
	public static String getLogPath(OpenVPNServer server){
		return BASE_LOG_DIR + "/" + server.getName() +  LOG_FILE_EXTENSION;
	}
	
	
	/**
	 * Returns the complete path of the supplied server Diffie Hellman file
	 * 
	 * @param server Server from who you want to know the Diffie Hellman file path
	 * @return Complete Diffie Hellman file path
	 */
	public static String getDHParamPath(OpenVPNServer server){
		return BASE_INSTANCE_DIR + "/" + server.getName() + "/" +  DHPARAM_FILE_NAME;
	}
	
	
	/**
	 * Returns the status of the supplied server (name)
	 * 
	 * @param serverName Server from who you want to know the status
	 * @return Status of the server
	 */
	public OpenVPNServerStatus getServerStatus(String serverName){
		return serversStatus.get(serverName);
	}
	
	
	/**
	 * Creates a new empty OpenVPN server
	 * 
	 * @return Empty openVPN server
	 */
	public OpenVPNServer createServer(){
		return new OpenVPNServer();
	}

	/**
	 * Installs a server instance on the OS
	 * 
	 * @param server Server to install
	 * @return True if all went right or false if it was not able to install
	 */
	public Boolean installServer(OpenVPNServer server){
		Boolean result = false;
		
		String serverName = server.getName();
		System.out.println("Creando la instancia");
		
		System.out.println(BASE_INSTANCE_DIR + "/" + serverName);
		
		// If the server is already installed, do nothing
		if (installedServers.containsKey(serverName))
			return false;
		
		
		if (new File(BASE_INSTANCE_DIR + "/" + serverName).mkdir()){
			System.out.println("- Directorio Creado");	
			try{
				
				// Generate Config File
				FileWriter fstream = new FileWriter(BASE_INSTANCE_DIR + "/" + serverName + "/" + CONF_FILE_NAME);
				BufferedWriter out = new BufferedWriter(fstream);
				
				out.write(OpenVPN2ConfigInterpreter.getInstance().
						generateConfigFile(
								server,
								BASE_INSTANCE_DIR + "/" + serverName,
								BASE_LOG_DIR,
								BASE_RUN_DIR));
				
				out.close();
				
				// Generate empty log file
				FileWriter fstream2 = new FileWriter(getLogPath(server));
				BufferedWriter out2 = new BufferedWriter(fstream2);
				out2.write("##LOG-BEGIN##");
				out2.close();
				
				// Start Diffie Hellman parameter creation (threaded)
				OpenVPNDHParamGenerator generator = 
					new OpenVPNDHParamGenerator(getDHParamPath(server), serverName, this);
				dhParamGenerators.put(serverName, generator);
				generator.start();
				
				// Set server status
				serversStatus.put(serverName, OpenVPNServerStatus.GENERATING_DH);
				
				// Add the server to the store
				installedServers.put(serverName, server);
				result = true;
				
				System.out.println("- Archivo de Configuración generado");
				
			}catch (Exception e){//Catch exception if any
				// Si no puedo crear el archivo, elimino el directorio de la instancia
				new File(server.getName()).delete();
				System.err.println("Error: " + e.getMessage());
			}

		}else{
			System.out.println("Failed dir creation");
		}
			
		
		return result;
		
	}
	

	/**
	 * Deletes an installed server instance. This implies removing all
	 * files from hard disk
	 * 
	 * @param serverName Name of the server to remove
	 */
	public void deleteServer(String serverName) {
		installedServers.remove(serverName);
		serversStatus.remove(serverName);
		//TODO remove files phisically
		
	}
	
	/**
	 * Starts an OpenVPN (installed) server instance.
	 * @param name Name of the server to start
	 * @throws Exception 
	 */
	public void stopServer(String serverName) throws Exception{
		ManagedProcess process = serverProcesses.get(serverName);
		if (process != null){
			//TODO ver una forma mas gentil de detener el proceso
			//process.destroy();
			System.out.println("Matando el proceso de la instancia " + serverName);
			process.killProcess();
			
			serversStatus.put(serverName, OpenVPNServerStatus.STOPPING);
		}
	}
	

	/**
	 * Starts an OpenVPN (installed) server instance.
	 * @param serverName Name of the server to start
	 * @throws Exception 
	 */
	//TODO mejorar la excepción que tira
	public void startServer(String serverName) throws Exception{
		
		//ProcessBuilder pb = new ProcessBuilder();
		
		String configFullPath = BASE_INSTANCE_DIR + "/" + serverName + "/" + CONF_FILE_NAME;
		
		System.out.println("Starting server " + serverName);
		
		//Only start the process if the instance exists and it's not running
		if ((installedServers.containsKey(serverName)) &&
				(!serverProcesses.containsKey(serverName))){
		
			String command = OPENVPN_FILE + " " + CONFIG_SWITCH + " " + configFullPath; 
			
			System.out.println(command);
			
			ManagedProcess process = new ManagedProcess(serverName, command);
			process.addProcessListener(this);
			process.start();
			
			serversStatus.put(serverName, OpenVPNServerStatus.RUNNING);
			serverProcesses.put(serverName, process);
		}
	}
	
	/*
	 * Returns installed server names
	 */
	public Set<String> getInstalledServerNames(){
		return installedServers.keySet();
	}
	
	/*
	 * Returns an already installed server by its name
	 */
	public OpenVPNServer getInstalledServer(String name){
		return installedServers.get(name);
	}
	
	public static OpenVPNServerManager getInstance(){
		if (INSTANCE == null){
			INSTANCE = new OpenVPNServerManager();
		}
		return INSTANCE;
	}


	/* (non-Javadoc)
	 * @see ar.com.gnuler.util.ManagedProcess.ProcessListener#processFinished(java.lang.String, ar.com.gnuler.util.ManagedProcess)
	 */
	public void processFinished(String serverName, ManagedProcess process) {
		System.out.println("The instance " + serverName + " has finished");
		serverProcesses.remove(serverName);
		serversStatus.put(serverName, OpenVPNServerStatus.STOPPED);
		
	}

	/* (non-Javadoc)
	 * @see ar.com.gnuler.vpn.openvpn.OpenVPNDHParamGenerator.DHParamGeneratorListener#processFinished(java.lang.String)
	 */
	public void dhGenerationProcessFinished(String serverName) {
		serversStatus.put(serverName, OpenVPNServerStatus.STOPPED);
		
	}
	
}
