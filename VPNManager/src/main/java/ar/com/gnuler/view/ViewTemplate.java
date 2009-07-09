package ar.com.gnuler.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class ViewTemplate extends WebPage{
	@SuppressWarnings("unchecked")
	public ViewTemplate(){
		add(new BookmarkablePageLink("linktoserverlist", OpenVPNServersView.class));
		add(new BookmarkablePageLink("linktonewserver", CreateOpenVPNServerView.class));
	}

}
