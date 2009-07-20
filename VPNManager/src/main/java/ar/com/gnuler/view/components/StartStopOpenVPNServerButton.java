package ar.com.gnuler.view.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;

import ar.com.gnuler.vpn.openvpn.OpenVPNServerInstance;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerInstance.OpenVPNServerStatus;



/*
 * Use:
 * <a href="#" wicket:id="@ID@link"><span wicket:id="@ID@label"></span></a>
 * 
 * Where @ID@ is the id passed to the constructor
 */
public class StartStopOpenVPNServerButton extends AjaxFallbackLink<String>{
	
	private static final long serialVersionUID = 1L;
	private OpenVPNServerInstance server;
	String action;
	
	public StartStopOpenVPNServerButton(String id, String serverName) {
		super(id + "link");
		
		server = OpenVPNServerManager.getInstance().getInstalledServer(serverName);
		
		//Generate the corresponding label depending of the current state
		
		OpenVPNServerStatus status = server.getServerStatus();
		
		
		if (status == OpenVPNServerStatus.RUNNING)
			action = "Stop";
		else if (status == OpenVPNServerStatus.STOPPED)
			action = "Start";
		else
			action = "Please wait...";

		add(new Label(id + "label", action));
	}

	
	public void onClick(AjaxRequestTarget target) {
        if (target != null) {
        	
        	OpenVPNServerStatus status = server.getServerStatus();
        	
        	//If is running stop the process, if not start it
        	try {
        		if (status == OpenVPNServerStatus.RUNNING)
        			server.stop();
        		else if (status == OpenVPNServerStatus.STOPPED)
        			server.start();
        		//else: do nothing.
        		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	       
        }
    }	

}
