package ar.com.gnuler.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import ar.com.gnuler.view.pki.PKIMainView;
import ar.com.gnuler.view.vpn.CreateOpenVPNServerView;
import ar.com.gnuler.view.vpn.OpenVPNMainView;

public class ViewTemplate extends WebPage{
	@SuppressWarnings("unchecked")
	public ViewTemplate(){
		add(new BookmarkablePageLink("linktoserverlist", OpenVPNMainView.class));
		add(new BookmarkablePageLink("linktonewserver", CreateOpenVPNServerView.class));
		add(new BookmarkablePageLink("linktopkimain", PKIMainView.class));
	}

}
