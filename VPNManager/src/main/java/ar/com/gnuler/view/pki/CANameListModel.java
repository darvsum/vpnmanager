package ar.com.gnuler.view.pki;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.com.gnuler.pki.CAStore;

class CANameListModel implements IDataProvider<String>{

	
	private static final long serialVersionUID = 1L;

	
	public Iterator<String> iterator(int first, int count) {
		return CAStore.getInstance().getCaNames().iterator();
	}

	public IModel<String> model(String object) {
		return new Model<String>(object);
	}

	public int size() {
		return CAStore.getInstance().getCaNames().size();
	}

	public void detach() {
		// TODO Auto-generated method stub
		
	}

	
}