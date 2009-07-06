package ar.com.gnuler.net.exceptions;

public class NotHostException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NotHostException(){
		super("The Address is not a host");
	}

}
