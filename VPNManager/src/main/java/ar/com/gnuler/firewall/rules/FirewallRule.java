package ar.com.gnuler.firewall.rules;

import ar.com.gnuler.firewall.codegenerator.CodeGenerator;

public interface FirewallRule {
	
	public static enum Action {ACCEPT, DENY, DROP};
	
	public String getName();
	
	public void setName(String name);
	
	public void setWeight(int weight);
	
	public int getWeight();
	
	public Boolean isImpliedBy(FirewallRule fule);
	
	public Action getAction();
	
	public void generateCode(CodeGenerator cg);

}
