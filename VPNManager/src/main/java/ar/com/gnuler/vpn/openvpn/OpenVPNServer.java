package ar.com.gnuler.vpn.openvpn;

import java.io.Serializable;
import java.util.ArrayList;

import ar.com.gnuler.net.IPV4Address;
import ar.com.gnuler.net.Protocol;
import ar.com.gnuler.net.exceptions.InvalidProtocolException;
import ar.com.gnuler.net.exceptions.NotHostException;
import ar.com.gnuler.net.exceptions.NotSubnetException;


// Represents an existing Instance of an OpenVPN Server
public class OpenVPNServer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static enum VirtualDeviceType {tun, tap};
	private String name;	//OpenVPN Instance name
	
	// OpenVPN Config Options
	private IPV4Address localIP;
	private Protocol protocol;
	private int port;
	private VirtualDeviceType dev;
	private String caFileName;
	private String certFileName;
	private String keyFileName;
	private String dhFileName;
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
	
	
	public OpenVPNServer (){
	}
	
	public OpenVPNServer (String name){
		this.name = name;
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
	public String getDhFileName() {
		return dhFileName;
	}
	public void setDhFileName(String dhFileName) {
		this.dhFileName = dhFileName;
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
