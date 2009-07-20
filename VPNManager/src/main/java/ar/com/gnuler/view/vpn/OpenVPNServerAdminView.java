package ar.com.gnuler.view.vpn;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;

import ar.com.gnuler.view.LogPanel;
import ar.com.gnuler.view.ViewTemplate;
import ar.com.gnuler.view.components.StartStopOpenVPNServerButton;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerInstance;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

public class OpenVPNServerAdminView extends ViewTemplate {
	
	private static String  serverName;
	AjaxFallbackLink<String> startStopLink;
	
	public OpenVPNServerAdminView(PageParameters parameters){
		
		serverName = parameters.getString("s");
		
		OpenVPNServerInstance server = OpenVPNServerManager.getInstance().getInstalledServer(serverName);
		
		// Create the title
		add(new Label("name","Server " + serverName ));
		add(new StartStopOpenVPNServerButton("startstop", serverName));
		add(new LogPanel("log", server.getLogPath()));
		
	}
	
	
}
