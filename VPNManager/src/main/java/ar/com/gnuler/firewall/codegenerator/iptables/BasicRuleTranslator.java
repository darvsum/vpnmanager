package ar.com.gnuler.firewall.codegenerator.iptables;

import ar.com.gnuler.firewall.rules.BasicFirewallRule;
import ar.com.gnuler.firewall.rules.FirewallRule;

public class BasicRuleTranslator extends RuleTranslator{

	NetworkObjectTranslator chain;
	
	public BasicRuleTranslator(){
		chain = new AddressTranslator();
	}
	
	@Override
	public String handleRule(FirewallRule rule) {
		
		if (rule instanceof BasicFirewallRule){
			
			BasicFirewallRule basicRule = (BasicFirewallRule)rule;
			
			String iptRule = IptablesCodeGenerator.IPTABLES_CMD;
			iptRule += " ";
			iptRule += chain.handleArgument(basicRule.getSource(), NetworkObjectTranslator.Objective.SOURCE);
			iptRule += " ";
			iptRule += chain.handleArgument(basicRule.getDestination(), NetworkObjectTranslator.Objective.DESTINATION);
			
			iptRule += " " + IptablesCodeGenerator.IPTABLES_ACTION_FLAG + " "; 
			
			//Mejorar esto
			if (rule.getAction() == FirewallRule.Action.ACCEPT)
					iptRule += IptablesCodeGenerator.IPTABLES_ACTION_ACCEPT;
			if (rule.getAction() == FirewallRule.Action.DENY)
				iptRule += IptablesCodeGenerator.IPTABLES_ACTION_DENY;
			if (rule.getAction() == FirewallRule.Action.DROP)
				iptRule += IptablesCodeGenerator.IPTABLES_ACTION_DROP;
			
			
			return iptRule;
		}
		else
			return super.handleRule(rule);
		
	}
	
}
