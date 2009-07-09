package ar.com.gnuler.view;

import java.util.List;
import java.util.Vector;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import ar.com.gnuler.util.LogTailer;
import ar.com.gnuler.view.components.DeleteOpenVPNServerButton;
import ar.com.gnuler.view.components.OpenVPNServersList;
import ar.com.gnuler.view.components.StartStopOpenVPNServerButton;
import ar.com.gnuler.vpn.openvpn.OpenVPNServer;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

public class OpenVPNServersView extends ViewTemplate {
	
	private WebMarkupContainer container;
	
	public OpenVPNServersView(){
		
		List<String> serverNameList = new Vector<String>(OpenVPNServerManager.getInstance().getInstalledServerNames());
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(new OpenVPNServersList("servers", serverNameList));
		
		add(container);

	}
}
