package ar.com.gnuler.model.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class  NetworkAddress extends NetObject{


	InetAddress network;
	InetAddress netmask;
	
	
	public NetworkAddress (String network, String netmask){
		
		//Name is the address
		super(network);
		
		try {
			this.network = InetAddress.getByName(network);
			this.netmask = InetAddress.getByName(netmask);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public NetworkAddress (String network){
		
		//Name is the address
		super(network);
		
		try {
			this.network = InetAddress.getByName(network);
			this.netmask = InetAddress.getByName("255.255.255.255");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@Override
	public String toString() {
		return network.toString();
		
	}

}
