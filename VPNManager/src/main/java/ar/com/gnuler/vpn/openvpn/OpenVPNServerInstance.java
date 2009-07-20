package ar.com.gnuler.vpn.openvpn;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.com.gnuler.net.IPV4Address;
import ar.com.gnuler.net.Protocol;
import ar.com.gnuler.net.exceptions.InvalidProtocolException;
import ar.com.gnuler.net.exceptions.NotHostException;
import ar.com.gnuler.net.exceptions.NotSubnetException;
import ar.com.gnuler.util.ManagedProcess;
import ar.com.gnuler.util.ManagedProcess.ProcessListener;


// Represents an existing Instance of an OpenVPN Server
public class OpenVPNServerInstance implements Serializable, ProcessListener {
	
	public static enum VirtualDeviceType {tun, tap};
	public enum OpenVPNServerStatus {RUNNING, STOPPED,STOPPING};
	private static final String BASE_INSTANCE_DIR = "/tmp/openvpn/instances";
	private static final String BASE_LOG_DIR = "/tmp/openvpn/log";
	private static final String CONF_FILE_NAME = "server.conf";
	private static final String OPENVPN_FILE = "/usr/sbin/openvpn";
	private static final String CONFIG_SWITCH = "--config";
	private static final String DHPARAM_FILE_NAME = "dh1024.pem";
	private static final String LOG_FILE_EXTENSION = ".log";
	private static final String BASE_RUN_DIR = "/tmp/openvpn/run";
	
	
	private static final long serialVersionUID = 1L;
	
	private String name;	//OpenVPN Instance name
	OpenVPNServerStatus status = OpenVPNServerStatus.STOPPED;;
	ManagedProcess process = null;
	
	// OpenVPN Config Options
	private IPV4Address localIP;
	private Protocol protocol;
	private int port;
	private VirtualDeviceType dev;
	private String caFileName;
	private String certFileName;
	private String keyFileName;
	private IPV4Address vpnSubnet;
	private ArrayList<IPV4Address> pushRoutes = new ArrayList<IPV4Address>();
	private ArrayList<IPV4Address> pushDNS = new ArrayList<IPV4Address>();
	private ArrayList<IPV4Address> pushWINS = new ArrayList<IPV4Address>();
	private Boolean persistKey;
	private Boolean persistTun;
	private String statusFileName;
	private Boolean appendLog;
	private int logVerbosity;
	private int managementPort;
	private IPV4Address managementAddress;
	private Boolean enableManagement;
	
	
	public OpenVPNServerInstance (){
	}
	
	public OpenVPNServerInstance (String name){
		this.name = name;
	}
	
	
	
	
	
	/**
	 * Returns the complete path of the supplied server log  file
	 * 
	 * @param server Server from who you want to know the log file path
	 * @return Complete log file path
	 */
	public String getLogPath(){
		return BASE_LOG_DIR + "/" + name +  LOG_FILE_EXTENSION;
	}
	
	
	/**
	 * Returns the complete path of the supplied server Diffie Hellman file
	 * 
	 * @param server Server from who you want to know the Diffie Hellman file path
	 * @return Complete Diffie Hellman file path
	 */
	public String getDHParamPath(){
		return BASE_INSTANCE_DIR + "/" + name + "/" +  DHPARAM_FILE_NAME;
	}
	
	
	/**
	 * Returns the status of the supplied server (name)
	 * 
	 * @param serverName Server from who you want to know the status
	 * @return Status of the server
	 */
	public OpenVPNServerStatus getServerStatus(){
		return status;
	}
	
	
	/**
	 * Starts an OpenVPN (installed) server instance.
	 * @param name Name of the server to start
	 * @throws Exception 
	 */
	public void stop() throws Exception{
		if (process != null){
			//TODO ver una forma mas gentil de detener el proceso
			process.killProcess();
			status = OpenVPNServerStatus.STOPPING;
		}
	}
	

	/**
	 * Starts an OpenVPN (installed) server instance.
	 * @param serverName Name of the server to start
	 * @throws Exception 
	 */
	public void start(){
		Logger logger = Logger.getLogger("openvpn");
		logger.log(Level.INFO, "Starting server " + name + ".");
		
		//Only start the process if the instance exists and it's not running
		if (process == null){
		
			logger.log(Level.INFO, "Creating Process with command line: " + getExecutionCommand());
			process = new ManagedProcess(name, getExecutionCommand());
			
			logger.log(Level.INFO, "Starting process");
			process.start();

			status = OpenVPNServerStatus.RUNNING;
			
		}else
			logger.log(Level.SEVERE, "Process seems to be running, so we do nothing.");
	}

	
	/**
	 * Returns the command nedded to execute to start the server instance
	 * @return The command nedded to execute to start the server instance
	 */
	private String getExecutionCommand(){
		return OPENVPN_FILE + " " + CONFIG_SWITCH + " " + getConfigFullPath();
	}
	
	/**
	 * Returns the configuration file full path
	 * @return The configuration file full path
	 */
	private String getConfigFullPath(){
		return BASE_INSTANCE_DIR + "/" + name + "/" + CONF_FILE_NAME;
	}
	
	
	/* (non-Javadoc)
	 * @see ar.com.gnuler.util.ManagedProcess.ProcessListener#processFinished(java.lang.String, ar.com.gnuler.util.ManagedProcess)
	 */
	public void processFinished(String serverName, ManagedProcess process) {
		Logger logger = Logger.getLogger("openvpn");
		logger.log(Level.INFO, "The instance " + serverName + " has finished");
		process = null;
		status = OpenVPNServerStatus.STOPPED;
		
	}
	
	
	public void writeConfigFile() throws IOException{
		FileWriter fstream = new FileWriter(BASE_INSTANCE_DIR + "/" + name + "/" + CONF_FILE_NAME);
		BufferedWriter out = new BufferedWriter(fstream);
		
		out.write(OpenVPN2ConfigInterpreter.getInstance().
				generateConfigFile(
						this,
						BASE_INSTANCE_DIR + "/" + name,
						BASE_LOG_DIR,
						BASE_RUN_DIR));
		
		out.close();
	}
	
	public void initializeLogFile() throws IOException{
		// Generate empty log file
		FileWriter fstream = new FileWriter(getLogPath());
		BufferedWriter out = new BufferedWriter(fstream);
		out.write("##LOG-BEGIN##");
		out.close();
	}
	
	// Getters y Setters

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public IPV4Address getLocalIP() {
		return localIP;
	}
	public void setLocalIP(IPV4Address localIP) throws NotHostException {

		// Only hosts are allowed as localIP
		if (!localIP.isHost())
			throw new NotHostException();
		
		this.localIP = localIP;
	}
	public Protocol getProtocol() {
		return protocol;
	}
	public void setProtocol(Protocol protocol) throws InvalidProtocolException {
		
		// OpenVPN only admits TCP or UDP
		if ((protocol != Protocol.TCP)
				&& (protocol != Protocol.UDP)){
			throw new InvalidProtocolException();
		}
		
		this.protocol = protocol;
	}
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public VirtualDeviceType getDev() {
		return dev;
	}
	public void setDev(VirtualDeviceType dev) {
		this.dev = dev;
	}
	public String getCaFileName() {
		return caFileName;
	}
	public void setCaFileName(String caFileName) {
		this.caFileName = caFileName;
	}
	public String getCertFileName() {
		return certFileName;
	}
	public void setCertFileName(String certFileName) {
		this.certFileName = certFileName;
	}
	public String getKeyFileName() {
		return keyFileName;
	}
	public void setKeyFileName(String keyFileName) {
		this.keyFileName = keyFileName;
	}
	
	public IPV4Address getVpnSubnet() {
		return vpnSubnet;
	}
	public void setVpnSubnet(IPV4Address vpnSubnet) throws NotSubnetException {
		if (vpnSubnet.isHost())
			throw new NotSubnetException();
		this.vpnSubnet = vpnSubnet;
	}
	
	public ArrayList<IPV4Address> getPushRoutes() {
		return pushRoutes;
	}
	public void addPushRoute(IPV4Address route) {
		this.pushRoutes.add(route);
	}
	public ArrayList<IPV4Address> getPushDNS() {
		return pushDNS;
	}
	public void addPushDNS(IPV4Address dns) throws NotHostException {
		if (!dns.isHost())
			throw new NotHostException();
		this.pushDNS.add(dns);
	}
	public ArrayList<IPV4Address> getPushWINS() {
		return pushWINS;
	}
	public void addPushWINS(IPV4Address wins) throws NotHostException {
		if (!wins.isHost())
			throw new NotHostException();
		this.pushWINS.add(wins);
	}
	public Boolean getPersistKey() {
		return persistKey;
	}
	public void setPersistKey(Boolean persistKey) {
		this.persistKey = persistKey;
	}
	public Boolean getPersistTun() {
		return persistTun;
	}
	public void setPersistTun(Boolean persistTun) {
		this.persistTun = persistTun;
	}
	public String getStatusFileName() {
		return statusFileName;
	}
	public void setStatusFileName(String statusFileName) {
		this.statusFileName = statusFileName;
	}
	public Boolean getAppendLog() {
		return appendLog;
	}
	public void setAppendLog(Boolean appendLog) {
		this.appendLog = appendLog;
	}
	public int getLogVerbosity() {
		return logVerbosity;
	}
	public void setLogVerbosity(int logVerbosity) {
		this.logVerbosity = logVerbosity;
	}
	public int getManagementPort() {
		return managementPort;
	}
	public void setManagementPort(int managementPort) {
		this.managementPort = managementPort;
	}
	public IPV4Address getManagementAddress() {
		return managementAddress;
	}
	public void setManagementAddress(IPV4Address managementAddress) {
		this.managementAddress = managementAddress;
	}
	public Boolean getEnableManagement() {
		return enableManagement;
	}
	public void setEnableManagement(Boolean enableManagement) {
		this.enableManagement = enableManagement;
	}
	
	
}
