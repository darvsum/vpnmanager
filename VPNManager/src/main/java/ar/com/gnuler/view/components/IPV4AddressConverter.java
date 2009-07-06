package ar.com.gnuler.view.components;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import ar.com.gnuler.net.IPV4Address;
import ar.com.gnuler.net.InvalidAddressFormatException;

public class IPV4AddressConverter implements IConverter {

	private static final long serialVersionUID = 1L;
	private boolean showMask = false;
	
	
	public void setShowMask(boolean showMask){
		this.showMask = showMask;
	}
	
	public IPV4AddressConverter(boolean showMask){
		this.showMask = showMask;
	}
	
	public Object convertToObject(String value, Locale locale) {
		
		IPV4Address address;
		try {
			address = IPV4Address.getAddressFromString(value);
		} catch (InvalidAddressFormatException e) {
			throw new ConversionException("The value is not a valid IP Address");
		}
		
		return address;
	}

	public String convertToString(Object value, Locale locale) {
	
		String ret = "";
		
		if (value instanceof IPV4Address){
			IPV4Address address = (IPV4Address) value;
			ret = address.getStringNet();
			
			if (showMask)
				ret += "/" + address.getStringMask();
			
		}
		
		return ret;
	}



}
