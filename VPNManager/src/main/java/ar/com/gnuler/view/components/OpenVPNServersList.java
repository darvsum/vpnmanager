package ar.com.gnuler.view.components;


import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import ar.com.gnuler.view.OpenVPNServerAdminView;
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
		String state;
		
		// Server Name label
		item.add(new Label("name", serverName));
		
		// Show server status
		if (OpenVPNServerManager.getInstance().isRunning(serverName))
	    	state = "Running";
	    else
	    	state = "Stopped";
	    item.add(new Label("state", state));
	    
	    // Delete button
	    item.add(new DeleteOpenVPNServerButton("delete", serverName, componentToUpdate));
	    
	    
	    // Start/Stop button
	    item.add(new StartStopOpenVPNServerButton("startstop", serverName));
	    
	    // Server Detail button
	    item.add(new BookmarkablePageLink<String>(
	    		"viewlog",
	    		OpenVPNServerAdminView.class, 
	    		new PageParameters("s=" + serverName)));
	
		
	}

}
