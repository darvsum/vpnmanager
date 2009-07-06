package ar.com.gnuler.firewall.codegenerator.iptables;

import ar.com.gnuler.firewall.rules.FirewallRule;

/*
 * Implements Chain of Responsibility Pattern
 */
public class RuleTranslator {
	
	RuleTranslator next;
	
	public RuleTranslator(){
		this.next = null;
	}
	
	public RuleTranslator(RuleTranslator next){
		this.next = next;
	}
	
	public String handleRule(FirewallRule rule){
		if (next != null)
			return next.handleRule(rule);
		else
			throw new RuntimeException("No handler for this rule type");
	}
	
}
