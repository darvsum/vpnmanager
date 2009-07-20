package ar.com.gnuler.vpn.openvpn;

import java.util.HashMap;
import java.util.Set;

import ar.com.gnuler.vpn.openvpn.OpenVPNServerInstaller.OpenVPNServerInstallerListener;

/**
 * @author matu
 *
 */
public class OpenVPNServerManager implements OpenVPNServerInstallerListener {
	
	
	// Singleton
	private static OpenVPNServerManager INSTANCE = null;
	
	// Contains the installed Servers
	private HashMap<String, OpenVPNServerInstance> installedServers = new HashMap<String, OpenVPNServerInstance>();
	
	public OpenVPNServerManager(){
		
	}
	
	
	/**
	 * Creates a new empty OpenVPN server
	 * 
	 * @return Empty openVPN server
	 */
	public OpenVPNServerInstance createServer(){
		return new OpenVPNServerInstance();
	}

	/**
	 * Installs a server instance on the OS
	 * 
	 * @param server Server to install
	 * @return True if all went right or false if it was not able to install
	 */
	public void installServer(OpenVPNServerInstance server){
		// If the server is already installed, do nothing
		if (!installedServers.containsKey(server.getName())){
			OpenVPNServerInstaller installer = new OpenVPNServerInstaller(server, this);
			installer.start();
		}
			
	}
	

	/**
	 * Deletes an installed server instance. This implies removing all
	 * files from hard disk
	 * 
	 * @param serverName Name of the server to remove
	 */
	public void deleteServer(String serverName) {
		
		//TODO remove files phisically
		//installedServers.remove(serverName);
		// Do nothing by now, not implemented yet
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
	public OpenVPNServerInstance getInstalledServer(String name){
		return installedServers.get(name);
	}
	
	public static OpenVPNServerManager getInstance(){
		if (INSTANCE == null){
			INSTANCE = new OpenVPNServerManager();
		}
		return INSTANCE;
	}


	/* (non-Javadoc)
	 * @see ar.com.gnuler.vpn.openvpn.OpenVPNServerInstaller.OpenVPNServerInstallerListener#serverInstallationFinished(ar.com.gnuler.vpn.openvpn.OpenVPNServerInstance)
	 */
	public void serverInstallationFinished(OpenVPNServerInstance server) {
		installedServers.put(server.getName(),server);
		
	}
	
}
