package ar.com.gnuler.net.exceptions;

public class NotSubnetException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public NotSubnetException(){
		super("Not a Subnet");
	}

}
