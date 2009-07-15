package ar.com.gnuler.view.components;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

class ServerNameListModel implements IDataProvider<String>{

	
	private static final long serialVersionUID = 1L;

	
	public Iterator<String> iterator(int first, int count) {
		return OpenVPNServerManager.getInstance().getInstalledServerNames().iterator();
	}

	public IModel<String> model(String object) {
		return new Model<String>(object);
	}

	public int size() {
		return OpenVPNServerManager.getInstance().getInstalledServerNames().size();
	}

	public void detach() {
		// TODO Auto-generated method stub
		
	}
	
}