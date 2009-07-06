package ar.com.gnuler.firewall.codegenerator.iptables;

import ar.com.gnuler.firewall.codegenerator.CodeGenerator;
import ar.com.gnuler.firewall.rules.FirewallRule;

public class IptablesCodeGenerator implements CodeGenerator {

	public final static String IPTABLES_CMD = "iptables";
	public final static String IPTABLES_SOURCE_ADDR = "--source";
	public final static String IPTABLES_DESTINATION_ADDR = "--destination";
	public final static String IPTABLES_ACTION_FLAG = "-j";
	public final static String IPTABLES_ACTION_ACCEPT = "ACCEPT";
	public final static String IPTABLES_ACTION_DENY = "ACCEPT";
	public final static String IPTABLES_ACTION_DROP = "DROP";
	
	
	private RuleTranslator chain;
	
	public IptablesCodeGenerator(){
		chain = new BasicRuleTranslator();
	}
	
	public void generateCode(FirewallRule fwRule) {
		
		System.out.println(chain.handleRule(fwRule));
	}

}
