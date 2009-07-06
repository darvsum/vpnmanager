package ar.com.gnuler.firewall.codegenerator.iptables;

import ar.com.gnuler.model.net.NetObject;
import ar.com.gnuler.model.net.NetworkAddress;

public class AddressTranslator extends NetworkObjectTranslator{

	@Override
	public String handleArgument(NetObject no,
			NetworkObjectTranslator.Objective objetive) {
		
		if (no instanceof NetworkAddress){
			NetworkAddress address = (NetworkAddress) no;
			
			String result = "";
			
			if (objetive == NetworkObjectTranslator.Objective.SOURCE)
				result = IptablesCodeGenerator.IPTABLES_SOURCE_ADDR;
			else
				result = IptablesCodeGenerator.IPTABLES_DESTINATION_ADDR;
				
			result += " " + address.toString();
			
			return result;
		}
		else
			return super.handleArgument(no, objetive);
	}

}
