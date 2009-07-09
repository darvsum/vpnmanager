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

public class OpenVPNServerManager implements ProcessListener {
	
	private static OpenVPNServerManager INSTANCE = null;
	private HashMap<String, OpenVPNServer> installedServers = new HashMap<String, OpenVPNServer>();
	private HashMap<String, ManagedProcess> serverProcesses = new HashMap<String, ManagedProcess>();
	
	//TODO remplazar por un archivo de property's
	
	private static final String BASE_INSTANCE_DIR = "/tmp/openvpn/instances";
	private static final String BASE_LOG_DIR = "/tmp/openvpn/log";
	private static final String BASE_RUN_DIR = "/tmp/openvpn/run";
	private static final String CONF_FILE_NAME = "server.conf";
	private static final String OPENVPN_FILE = "/usr/sbin/openvpn";
	private static final String CONFIG_SWITCH = "--config";
	
	
	public OpenVPNServerManager(){
		
	}
	
	public static String getLogPath(OpenVPNServer server){
		//TODO obtener la extension 
		return BASE_LOG_DIR + "/" + server.getName() + ".log";
	}
	
	/*
	 * Creates a new empty OpenVPNServer
	 */
	public OpenVPNServer createServer(){
		return new OpenVPNServer();
	}

	/*
	 * Installs a server instance on the OS
	 */
	public Boolean installServer(OpenVPNServer server){
		Boolean result = false;
		
		System.out.println("Creando la instancia");
		
		System.out.println(BASE_INSTANCE_DIR + "/" + server.getName());
		
		// If the server is already installed, do nothing
		if (installedServers.containsKey(server.getName()))
			return false;
		
		
		if (new File(BASE_INSTANCE_DIR + "/" + server.getName()).mkdir()){
			System.out.println("- Directorio Creado");	
			try{
				
				FileWriter fstream = new FileWriter(BASE_INSTANCE_DIR + "/" + server.getName() + "/" + CONF_FILE_NAME);
				BufferedWriter out = new BufferedWriter(fstream);
				
				out.write(OpenVPN2ConfigInterpreter.getInstance().
						generateConfigFile(
								server,
								BASE_INSTANCE_DIR + "/" + server.getName(),
								BASE_LOG_DIR,
								BASE_RUN_DIR));
				
				out.close();
				installedServers.put(server.getName(), server);
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
	 * Starts an OpenVPN (installed) server instance.
	 * @param name Name of the server to start
	 * @throws Exception 
	 */
	public void stopServer(String name) throws Exception{
		ManagedProcess process = serverProcesses.get(name);
		if (process != null){
			//TODO ver una forma mas gentil de detener el proceso
			//process.destroy();
			System.out.println("Matando el proceso de la instancia " + name);
			process.killProcess();
			
		}
	}
	
	
	/**
	 * Starts an OpenVPN (installed) server instance.
	 * @param name Name of the server to start
	 * @throws Exception 
	 */
	//TODO mejorar la excepción que tira
	public void startServer(String name) throws Exception{
		
		//ProcessBuilder pb = new ProcessBuilder();
		
		String configFullPath = BASE_INSTANCE_DIR + "/" + name + "/" + CONF_FILE_NAME;
		
		System.out.println("Starting server " + name);
		
		//Only start the process if the instance exists and it's not running
		if ((installedServers.containsKey(name)) &&
				(!serverProcesses.containsKey(name))){
		
			String command = OPENVPN_FILE + " " + CONFIG_SWITCH + " " + configFullPath; 
			
			System.out.println(command);
			
			ManagedProcess process = new ManagedProcess(name, command);
			process.addProcessListener(this);
			process.start();
			
			
			serverProcesses.put(name, process);
		}
	}
	
	
	public boolean isRunning(String name){
		return serverProcesses.containsKey(name);
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

	public void deleteServer(String serverName) {
		installedServers.remove(serverName);
		
	}

	// Here we recieve notifications when a process finished
	public void processFinished(String id, ManagedProcess process) {
		System.out.println("The instance " + id + " has finished");
		serverProcesses.remove(id);
		
	}
	
}
