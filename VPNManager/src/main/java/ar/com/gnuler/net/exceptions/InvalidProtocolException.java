package ar.com.gnuler.net.exceptions;

public class InvalidProtocolException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidProtocolException(){
		super ("The protocol is not valid");
	}

}
