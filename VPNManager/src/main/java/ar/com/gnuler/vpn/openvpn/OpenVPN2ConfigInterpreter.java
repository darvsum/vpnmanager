package ar.com.gnuler.vpn.openvpn;

import java.io.Serializable;

import ar.com.gnuler.net.IPV4Address;
import ar.com.gnuler.net.Protocol;


// OpenVPN 2.0 Config File Generator, and maybe someday parser... ;)
public class OpenVPN2ConfigInterpreter implements IOpenVPNConfigFileInterpreter, Serializable{
	
	private static final long serialVersionUID = -4482559440859755325L;
	//Lexical Constants
	private static final String NEWLINE = "\n";
	private static final String SPACER = " ";
	private static final String LOCAL_IP = "local";
	private static final String PORT = "port";
	private static final String PROTOCOL = "proto";
	private static final String PROTOCOL_TCP = "tcp";
	private static final String PROTOCOL_UDP = "udp";
	private static final String DEVICE = "dev";
	private static final String CA = "ca";
	private static final String CERTIFICATE = "cert";
	private static final String KEY = "key";
	private static final String DIFFIE_HELLMAN = "dh";
	private static final String VPN_RANGE = "server";
	
	private static final String PUSH_ROUTE_BEGIN  = "push \"route ";
	private static final String PUSH_ROUTE_END  = "\"";
	
	private static final String PUSH_DNS_BEGIN  = "push \"dhcp-option DNS ";
	private static final String PUSH_DNS_END  = "\"";
	
	
	private static final String PUSH_WINS_BEGIN  = "push \"dhcp-option WINS ";
	private static final String PUSH_WINS_END  = "\"";
	
	
	private static final String STATUS = "status";
	private static final String LOG = "log";
	private static final String LOG_APPEND = "log-append";
	private static final String VERBOSITY = "verb";
	private static final String MANAGEMENT = "management";
	
	private static final String PERSISTENT_POOL = "ifconfig-pool-persist";
	private static final String PERSIST_KEY = "persist-key";
	private static final String PERSIST_TUN = "persist-tun";
	
	private static final String BEGIN_MARK = "##BEGIN##";
	private static final String END_MARK = "##END##";
	private static final String MODIFICATION_ADVISORY_0 = "#####################################################################";
	private static final String MODIFICATION_ADVISORY_1 = "# ATTENTION: Do not modify this file directly using an editor,      #";
	private static final String MODIFICATION_ADVISORY_2 = "# all changes will be overwritten by the OPENVPN administrator.     #";
	private static final String MODIFICATION_ADVISORY_3 = "#####################################################################";
	
	private static final String LOG_FILE_EXTENSION = ".log";
	private static final String PERSISTENT_POOL_FILE_EXTENSION = ".pool"; 
	
	private static OpenVPN2ConfigInterpreter INSTANCE = null;
	
	public static OpenVPN2ConfigInterpreter getInstance(){
		if (INSTANCE == null)
			INSTANCE = new OpenVPN2ConfigInterpreter();
		
		return INSTANCE;
	}
	
	/* (non-Javadoc)
	 * @see ar.com.gnuler.vpnmanager.iOpenVPNConfigFile#generateConfigFile()
	 */
	public String generateConfigFile(OpenVPNServerInstance server, String baseConfigPath, String baseLogPath, String baseRunPath){
		
		String config = "";
		
		config += BEGIN_MARK + NEWLINE + NEWLINE;
		
		config += MODIFICATION_ADVISORY_0 + NEWLINE;
		config += MODIFICATION_ADVISORY_1 + NEWLINE;
		config += MODIFICATION_ADVISORY_2 + NEWLINE;
		config += MODIFICATION_ADVISORY_3 + NEWLINE;
		
		config += NEWLINE + NEWLINE;
		
		// local xxx.xxx.xxx.xxx
		config += LOCAL_IP + SPACER + server.getLocalIP().getStringNet() + NEWLINE;
		
		// port xxxx
		config += PORT + SPACER + server.getPort() + NEWLINE;
		
		// proto tcp, proto udp
		if (server.getProtocol() == Protocol.UDP){
			config += PROTOCOL + SPACER + PROTOCOL_UDP + NEWLINE;
		}else{
			config += PROTOCOL + SPACER + PROTOCOL_TCP + NEWLINE;
		}
		
		
		// dev tun, dev tap
		config  += DEVICE + SPACER + server.getDev() + NEWLINE;
		
		// ca ca.crt
		config += CA + SPACER + baseConfigPath + "/" + server.getCaFileName() + NEWLINE;
		
		// cert server.crt
		config += CERTIFICATE + SPACER + baseConfigPath + "/" + server.getCertFileName() + NEWLINE;
		
		// key server.key
		config += KEY + SPACER + baseConfigPath + "/" + server.getKeyFileName() + NEWLINE;
		
		// dh file.dh
		config += DIFFIE_HELLMAN + SPACER + server.getDHParamPath() + NEWLINE;
		
		// server xxx.xxx.xxx.xxx yyy.yyy.yyy.yyy
		config += VPN_RANGE + SPACER + server.getVpnSubnet().getStringNet() + SPACER
				+ server.getVpnSubnet().getStringMask() + NEWLINE;
		
		// ifconfig-pool-persist /var/run/openvpn/ipp2.txt
		config += PERSISTENT_POOL + SPACER + baseRunPath + "/" + server.getName() + PERSISTENT_POOL_FILE_EXTENSION + NEWLINE;
		
		// push "route 10.0.0.0 255.0.0.0"
		for (IPV4Address addr: server.getPushRoutes()){
			config += PUSH_ROUTE_BEGIN + addr.getStringNet() + SPACER + addr.getStringMask() + PUSH_ROUTE_END + NEWLINE;
		}
		
		//push "dhcp-option DNS 10.10.1.180"
		for (IPV4Address addr: server.getPushDNS()){
			config += PUSH_DNS_BEGIN + addr.getStringNet() + PUSH_DNS_END + NEWLINE;
		}
		
		//push "dhcp-option WINS 10.8.0.1"
		for (IPV4Address addr: server.getPushWINS()){
			config += PUSH_WINS_BEGIN + addr.getStringNet() + PUSH_WINS_END + NEWLINE;
		}
		
		config += "keepalive 10 120" + NEWLINE; //TODO agregar al modelo
		config += "comp-lzo" + NEWLINE; //TODO agregar al modelo
		
		
		//persist-key
		if (server.getPersistKey()){
			config += PERSIST_KEY + NEWLINE;
		}
	
		//persist-tun
		if (server.getPersistTun()){
			config += PERSIST_TUN + NEWLINE;
		}
		
		config += STATUS + SPACER + server.getStatusFileName() + NEWLINE;
		
		if (server.getAppendLog()){
			config += LOG_APPEND + SPACER + baseLogPath + "/" + server.getName() + LOG_FILE_EXTENSION + NEWLINE;
		}else {
			config += LOG + SPACER + baseLogPath + "/" + server.getName() + LOG_FILE_EXTENSION + NEWLINE;
		}
		
		config += VERBOSITY + SPACER + server.getLogVerbosity() + NEWLINE;
		
		if (server.getEnableManagement()){
			config += MANAGEMENT + SPACER + server.getManagementAddress().getStringNet() +
				SPACER +  server.getManagementPort() + NEWLINE;
		}
		
		config +=  NEWLINE + END_MARK + NEWLINE;
		
		return config;
	}
	

}
