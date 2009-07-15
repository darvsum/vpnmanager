package ar.com.gnuler.view.pki;


import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.com.gnuler.pki.CAStore;
import ar.com.gnuler.pki.CertificateAuthority;
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

public class CAList extends DataView<String> {

	private static final long serialVersionUID = 1L;
	Component componentToUpdate;
		
	
	public CAList(String id, Component componentToUpdate) {
		super(id, new CANameListModel());
		setOutputMarkupId(true);
		this.componentToUpdate = componentToUpdate;
	}

	

	@Override
	protected void populateItem(Item<String> item) {
		String caName = item.getModelObject();
//		String state;
		
		// Server Name label
		item.add(new Label("name", caName));
		
		// Show server status
//		if (OpenVPNServerManager.getInstance().isRunning(serverName))
//	    	state = "Running";
//	    else
//	    	state = "Stopped";
//	    item.add(new Label("state", state));
	    
	    // Delete button
//	    item.add(new DeleteOpenVPNServerButton("delete", serverName, componentToUpdate));
	    
	    
	    // Start/Stop button
//	    item.add(new StartStopOpenVPNServerButton("startstop", serverName));
	    
	    // Server Detail button
//	    item.add(new BookmarkablePageLink<String>(
//	    		"viewlog",
//	    		OpenVPNServerAdminView.class, 
//	    		new PageParameters("s=" + serverName)));
	
		
	}

}
