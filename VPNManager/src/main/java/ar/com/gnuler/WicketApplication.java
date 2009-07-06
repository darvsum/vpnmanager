package ar.com.gnuler;

import org.apache.wicket.protocol.http.WebApplication;
import ar.com.gnuler.view.CreateOpenVPNServerView;

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
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<CreateOpenVPNServerView> getHomePage()
	{
		//return HomePage.class;
		//return Dashboard.class;
		return CreateOpenVPNServerView.class;
	}

}
