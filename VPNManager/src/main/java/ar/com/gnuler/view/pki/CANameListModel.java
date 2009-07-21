package ar.com.gnuler.view.pki;

import java.security.KeyStoreException;
import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.com.gnuler.pki.IdentityStore;

class CANameListModel implements IDataProvider<String>{

	
	private static final long serialVersionUID = 1L;

	
	public Iterator<String> iterator(int first, int count) {
		
		Iterator<String> it = null;
		
		try {
			it =  IdentityStore.getInstance().getAliases().iterator();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return it;
	}

	public IModel<String> model(String object) {
		return new Model<String>(object);
	}

	public int size() {
		int size = 0;
		
		try {
			size =  IdentityStore.getInstance().getAliases().size();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return size;
	}

	public void detach() {
		// TODO Auto-generated method stub
		
	}

	
}