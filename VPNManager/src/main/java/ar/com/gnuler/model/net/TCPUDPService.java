package ar.com.gnuler.model.net;

import ar.com.gnuler.model.net.Service;
	
public class TCPUDPService extends Service {
	public static enum Protocol {TCP, UDP};
	
	private Protocol protocol;
	private int port;
	
	public TCPUDPService(String name, Protocol proto, int port){
		super(name);
		this.protocol = proto;
		this.port = port;
	}
	
	
	public int getPort(){
		return port;
	}
	
	public Protocol getProtocol(){
		return protocol;
	}


	@Override
	public String toString() {
		String result = "";
		if (protocol == Protocol.TCP)
			result = "TCP";
		else
			result = "UDP";
		
		result += " " + port;
		
		return result;
	}

}
