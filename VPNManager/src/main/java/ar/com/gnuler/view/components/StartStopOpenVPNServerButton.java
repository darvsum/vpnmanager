package ar.com.gnuler.view.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;

import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;


/*
 * Use:
 * <a href="#" wicket:id="@ID@link"><span wicket:id="@ID@label"></span></a>
 * 
 * Where @ID@ is the id passed to the constructor
 */
public class StartStopOpenVPNServerButton extends AjaxFallbackLink<String>{
	
	private static final long serialVersionUID = 1L;
	private String serverName;
	String state;
	
	public StartStopOpenVPNServerButton(String id, String serverName) {
		super(id + "link");
		this.serverName = serverName;
		
		//Generate the corresponding label depending of the current state
		if (OpenVPNServerManager.getInstance().isRunning(serverName))
		   	state = "Stop";
		else
		   	state = "Start";
		    
		add(new Label(id + "label", state));
	}

	
	public void onClick(AjaxRequestTarget target) {
        if (target != null) {
        	
        	//If is running stop the process, if not start it
        	try {
        		if (OpenVPNServerManager.getInstance().isRunning(serverName))
					OpenVPNServerManager.getInstance().stopServer(serverName);
			    else
			    	OpenVPNServerManager.getInstance().startServer(serverName);
        		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	       
        }
    }	

}
