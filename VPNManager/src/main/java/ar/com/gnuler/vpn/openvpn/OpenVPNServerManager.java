package ar.com.gnuler.vpn.openvpn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class OpenVPNServerManager {
	
	private static OpenVPNServerManager INSTANCE = null;
	private HashMap<String, OpenVPNServer> installedServers = new HashMap<String, OpenVPNServer>();
	private HashMap<String, Process> serverProcesses = new HashMap<String, Process>();
	
	//TODO remplazar por un archivo de property's
	
	private final String BASE_INSTANCE_DIR = "/tmp/openvpn/instances";
	private final String BASE_LOG_DIR = "/tmp/openvpn/log";
	private final String BASE_RUN_DIR = "/tmp/openvpn/run";
	private final String CONF_FILE_NAME = "server.conf";
	private final String OPENVPN_FILE = "usr/sbin/openvpn";
	private final String CONFIG_SWITCH = "--config";
	
	
	public OpenVPNServerManager(){
		
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
		
		if (new File(BASE_INSTANCE_DIR + "/" + server.getName()).mkdir()){
			System.out.println("- Directorio Creado");	
			try{
				
				FileWriter fstream = new FileWriter(BASE_INSTANCE_DIR + "/" + server.getName() + "/" + CONF_FILE_NAME);
				BufferedWriter out = new BufferedWriter(fstream);
				
				out.write(OpenVPN2ConfigInterpreter.getInstance().
						generateConfigFile(
								server,
								BASE_INSTANCE_DIR + "/" + server.getName(),
								BASE_LOG_DIR + "/" + server.getName(),
								BASE_RUN_DIR + "/" + server.getName()));
				
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
	public void startServer(String name) throws Exception{
		ProcessBuilder pb = new ProcessBuilder();
		String configFullPath = BASE_INSTANCE_DIR + "/" + name + "/" + CONF_FILE_NAME;
		
		
		//TODO customize exception
		if (!serverProcesses.containsKey(name))
			throw new Exception("No such server");
		
		pb.command(OPENVPN_FILE + " " + CONFIG_SWITCH + " " +  configFullPath);
		try {
			Process process = pb.start();
			serverProcesses.put(name, process);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
}
