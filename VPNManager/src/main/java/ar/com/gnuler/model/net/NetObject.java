package ar.com.gnuler.model.net;

import java.net.Inet4Address;

import java.util.ArrayList;
import java.util.List;


public abstract class NetObject {
	
	//TODO a ser reemplazado por hibernate
	private static ArrayList<NetObject> netObjects = new ArrayList<NetObject>();

	private String name;
	
	public NetObject(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	
	public abstract String toString();
	
	// Persistencia
	//TODO usar Hibernate
	public static ArrayList<NetObject> getSaved(){
		return netObjects;
	}
	
	public static ArrayList<NetworkAddress> getSavedAddresses(){
		
		ArrayList<NetworkAddress> addresses = new ArrayList<NetworkAddress>();
		
		for (NetObject no: netObjects){
			if (no instanceof NetworkAddress)
				addresses.add((NetworkAddress) no);
			
		}
		return addresses;
	}
	
	public static ArrayList<NetObjectGroup> getSavedGroups(){
		
		ArrayList<NetObjectGroup> addresses = new ArrayList<NetObjectGroup>();
		
		for (NetObject no: netObjects){
			if (no instanceof NetObjectGroup)
				addresses.add((NetObjectGroup) no);
			
		}
		return addresses;
	}
	
	public static void save(NetObject netObject){
		netObjects.add(netObject);
	}
	
	
	
	
	
}
