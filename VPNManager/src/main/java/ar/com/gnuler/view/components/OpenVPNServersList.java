package ar.com.gnuler.view.components;


import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

import ar.com.gnuler.view.vpn.OpenVPNServerAdminView;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerInstance;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

/*
 * Example usage:
 * 
 * <tr wicket:id="servers">
 *		<td><span wicket:id="name">Test ID</span></td>
 *		<td><span wicket:id="state">Test ID</span></td>
 *		<td><a href="#" wicket:id="delete">delete</a></td>
 *		<td><a href="#" wicket:id="startstoplink"><span wicket:id="startstoplabel">Test ID</span></a></td>
 *		<td><a href="#" wicket:id="viewlog">view log</a></td>
 *	</tr>
 *
 */

public class OpenVPNServersList extends DataView<String> {

	private static final long serialVersionUID = 1L;
	Component componentToUpdate;
		
	public OpenVPNServersList(String id, Component componentToUpdate) {
		super(id, new ServerNameListModel());
		setOutputMarkupId(true);
		this.componentToUpdate = componentToUpdate;
	}

	

	@Override
	protected void populateItem(Item<String> item) {
		String serverName = item.getModelObject();
		OpenVPNServerInstance server = OpenVPNServerManager.getInstance().getInstalledServer(serverName);
		
		// Server Name label
		item.add(new Label("name", serverName));
		
		// Show server status
	    item.add(new Label("state", server.getServerStatus().toString()));
	    
	    // Delete button
	    item.add(new DeleteOpenVPNServerButton("delete", serverName, componentToUpdate));
	    
	    
	    // Start/Stop button
	    item.add(new StartStopOpenVPNServerButton("startstop", serverName));
	    
	    // Server Detail button
	    item.add(new BookmarkablePageLink<String>(
	    		"admin",
	    		OpenVPNServerAdminView.class, 
	    		new PageParameters("s=" + serverName)));
	
		
	}

}
