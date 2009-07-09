package ar.com.gnuler;

import org.apache.wicket.protocol.http.WebApplication;
import ar.com.gnuler.view.CreateOpenVPNServerView;
import ar.com.gnuler.view.LogView;
import ar.com.gnuler.view.OpenVPNServersView;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see ar.com.gnuler.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    
    /**
     * Constructor
     */
	public WicketApplication()
	{
		
	}
	
	public void init(){
		mountBookmarkablePage("/vpn/newserver", CreateOpenVPNServerView.class);
		mountBookmarkablePage("/vpn", OpenVPNServersView.class);
		mountBookmarkablePage("/logs", LogView.class);
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<OpenVPNServersView> getHomePage()
	{
		
		//return CreateOpenVPNServerView.class;
		return OpenVPNServersView.class;
	}

}
