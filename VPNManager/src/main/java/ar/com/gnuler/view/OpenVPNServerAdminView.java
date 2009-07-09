package ar.com.gnuler.view;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;

import ar.com.gnuler.view.components.StartStopOpenVPNServerButton;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

public class OpenVPNServerAdminView extends ViewTemplate {
	
	private static String  serverName;
	AjaxFallbackLink<String> startStopLink;
	
	public OpenVPNServerAdminView(PageParameters parameters){
		
		
		
		
		
		serverName = parameters.getString("s");
		
		// Create the title
		add(new Label("name","Server " + serverName ));
		add(new StartStopOpenVPNServerButton("startstop", serverName));
		
		
	}
	
	
}
