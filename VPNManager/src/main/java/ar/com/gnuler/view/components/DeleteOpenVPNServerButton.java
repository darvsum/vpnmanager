package ar.com.gnuler.view.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;


/*
 * Use:
 * <a href="#" wicket:id="@ID@link"><span wicket:id="@ID@label"></span></a>
 * 
 * Where @ID@ is the id passed to the constructor
 */
public class DeleteOpenVPNServerButton extends AjaxFallbackLink<String>{
	
	private static final long serialVersionUID = 1L;
	private String serverName;
	String state;
	
	public DeleteOpenVPNServerButton(String id, String serverName) {
		super(id);
		this.serverName = serverName;
		
	}

	public void onClick(AjaxRequestTarget target) {
		 if (target != null) {
         	OpenVPNServerManager.getInstance().deleteServer(serverName);
         }
    }	

}
