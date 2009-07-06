package ar.com.gnuler.model.net;

import java.util.ArrayList;
import java.util.List;

public class NetObjectGroup<T extends NetObject> extends NetObject {

	private List<T> netObjects;
	
	public NetObjectGroup(String name) {
		super(name);
		netObjects = new ArrayList<T>();
	}

	public void add(T no){
		netObjects.add(no);
	}
	
	public void remove(T no){
		netObjects.remove(no);
	}
	
	public List<T> getChildren(){
		return netObjects;
	}
	@Override
	public String toString() {
		
		String result = "[";
		
		for (T no: netObjects){
			result += "," + no.toString();
		}
		
		result += "]";
		return result;
	}

}
