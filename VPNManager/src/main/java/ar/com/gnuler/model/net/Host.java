package ar.com.gnuler.model.net;


public class Host extends NetObject {

	private NetworkAddress address;
	
	public Host(String name, NetworkAddress address) {
		super(name);
			this.address = address;		
	}
	
	public NetworkAddress getAddress(){
		return address;
	}

	@Override
	public String toString() {
		return address.toString();
	}

}
