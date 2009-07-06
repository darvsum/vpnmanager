package ar.com.gnuler.vpn.openvpn;

import java.util.HashMap;
import java.util.Set;

public class OpenVPNServerManager {
	
	private static OpenVPNServerManager INSTANCE = null;
	private HashMap<String, OpenVPNServer> installedServers = new HashMap<String, OpenVPNServer>();
	
	
	public OpenVPNServerManager(){
		
	}
	
	/*
	 * Creates a new empty OpenVPNServer
	 */
	public OpenVPNServer createServer(){
		return new OpenVPNServer(new OpenVPN2ConfigInterpreter());
	}

	/*
	 * Installs a server instance on the OS
	 */
	public void installServer(OpenVPNServer server){
		installedServers.put(server.getName(), server);
		
		//TODO hacer efectiva la instalación de la instancia en el SO
		
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
