package ar.com.gnuler.model.net;

import java.util.ArrayList;
import java.util.List;


public class ServiceGroup extends Service {
	
	private ArrayList<Service> services;
		
	public ServiceGroup(String name){
		super(name);
		services = new ArrayList<Service>();
	}
	
	public void add(Service s){
		services.add(s);
	}
	
	public void remove(Service s){
		services.remove(s);
	}
	
	public List<Service> getChildren(){
		return services;
	
	}
	
	@Override
	public String toString() {
		
		String result = "[";
		
		for (Service s: services){
			result += "," + s.toString();
		}
		
		result += "]";
		return result;
	}
}
