package ar.com.gnuler.view.components;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.model.IModel;

import ar.com.gnuler.net.IPV4Address;

public class IPV4AddressField extends TextField<IPV4Address> {

	private static final long serialVersionUID = 1L;

	public IPV4AddressField(String id, IModel<IPV4Address> model) {
		super(id, model, IPV4Address.class);
	}

	@Override
	public IConverter getConverter(Class<?> type) {
		return new IPV4AddressConverter();
	}


}
