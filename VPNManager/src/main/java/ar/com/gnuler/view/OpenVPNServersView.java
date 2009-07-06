package ar.com.gnuler.view;

import java.util.List;
import java.util.Vector;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

public class OpenVPNServersView extends ViewTemplate {
	public OpenVPNServersView(){
		
		List<String> serverNameList = new Vector<String>(OpenVPNServerManager.getInstance().getInstalledServerNames());
		
		add(new ListView<String>("servers",serverNameList )
	     {
			private static final long serialVersionUID = 1L;

			public void populateItem(final ListItem<String> listItem){
			    final String serverName = (String)listItem.getModelObject();
			    listItem.add(new Label("name", serverName));
			    
			    String state;
			    
			    if (OpenVPNServerManager.getInstance().isRunning(serverName))
			    	state = "Running";
			    else
			    	state = "Stopped";
			    
			    listItem.add(new Label("state", state));
			    	

	            }
	        });

	}
}
