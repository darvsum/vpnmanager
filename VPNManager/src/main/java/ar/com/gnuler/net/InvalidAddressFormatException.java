package ar.com.gnuler.net;

public class InvalidAddressFormatException extends Exception {
	private static final long serialVersionUID = -4914352294228299986L;

	public InvalidAddressFormatException (String s){
		super(s);
	}
	
}
