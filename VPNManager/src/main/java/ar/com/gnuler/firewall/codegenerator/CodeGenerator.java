package ar.com.gnuler.firewall.codegenerator;

import ar.com.gnuler.firewall.rules.FirewallRule;

public interface CodeGenerator {
	public void generateCode(FirewallRule fwRule);
}
