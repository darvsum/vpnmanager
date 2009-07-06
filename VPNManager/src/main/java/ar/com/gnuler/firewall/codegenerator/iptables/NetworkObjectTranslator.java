package ar.com.gnuler.firewall.codegenerator.iptables;

import ar.com.gnuler.firewall.rules.FirewallRule;
import ar.com.gnuler.model.net.NetObject;

public class NetworkObjectTranslator {
	NetworkObjectTranslator next;
	public static enum Objective {SOURCE, DESTINATION};
	
	public NetworkObjectTranslator(){
		this.next = null;
	}
	
	public NetworkObjectTranslator(NetworkObjectTranslator next){
		this.next = next;
	}
	
	public String handleArgument(NetObject no,
			NetworkObjectTranslator.Objective objetive){
		if (next != null)
			return next.handleArgument(no, objetive);
		else
			throw new RuntimeException("No handler for this Network Object Type type");
	}
}
