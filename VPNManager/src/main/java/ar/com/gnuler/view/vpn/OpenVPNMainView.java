package ar.com.gnuler.view.vpn;

import org.apache.wicket.markup.html.WebMarkupContainer;

import ar.com.gnuler.view.ViewTemplate;
import ar.com.gnuler.view.components.OpenVPNServersList;

public class OpenVPNMainView extends ViewTemplate {
	
	private WebMarkupContainer container;
	
	public OpenVPNMainView(){
		
//		List<String> serverNameList = new Vector<String>(OpenVPNServerManager.getInstance().getInstalledServerNames());
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(new OpenVPNServersList("servers", container));
		
		add(container);

	}
}
