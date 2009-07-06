package ar.com.gnuler.firewall.rules;

import ar.com.gnuler.firewall.codegenerator.CodeGenerator;
import ar.com.gnuler.model.net.NetObject;


/*
 * A Basic Firewall Rule is one who Accepts, Drop, or Deny
 * an IP packet based on a Source and a Destination NetObject.
 */
public class BasicFirewallRule implements FirewallRule{

	NetObject source;
	NetObject destination;
	String name;
	int weight = 100; // Default weight
	Action action = FirewallRule.Action.DENY; // Deny by default

	public BasicFirewallRule(String name, 
			NetObject source, NetObject destination){
		this.source = source;
		this.destination = destination;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}
		
	public void setSource(NetObject source){
		this.source = source;
	}
	
	public NetObject getSource(){
		return source;
	}
	
	public void setDestination(NetObject destination){
		this.destination = destination;
	}
	
	
	public NetObject getDestination(){
		return destination;
	}
	
	public void generateCode(CodeGenerator cg) {
		cg.generateCode(this);
	}

	public Boolean isImpliedBy(FirewallRule fule) {
		throw new RuntimeException("Not Implemented yet");
	}

	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action){
		this.action = action;
	}


}
