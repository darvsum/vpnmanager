package ar.com.gnuler.net;

import java.io.Serializable;
import java.util.IllegalFormatException;
import java.util.UnknownFormatConversionException;
import java.util.regex.Pattern;


public class  IPV4Address implements Serializable{
	
	static Pattern pattern = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");
	private static final long serialVersionUID = 1667239185833975673L;
	private int net;
	private int mask;
	
	public IPV4Address(int net, int mask) {
		this.net  = net;
		this.mask = mask;
	}

	public String getAddress(){
		return getStringNet() + "/" + getStringMask();
	}
	
	public String getStringNet(){
		return byteToString(getNet());
		
	}
	
	public String getStringMask(){
		return byteToString(getMask());
	}
	
	public byte[] getNet(){
		return intToByte(net);
	}
	
	public byte[] getMask(){
		return intToByte(mask);
	}
	
	public Boolean isHost(){
		return (mask == -1); // -1 == 255.255.255.255
	}
	
	
	/*
	 * Supported formats are:
	 * 	192.168.1.1 -> single host address
	 *  192.168.1.0/255.255.255.0 -> Address/Netmask
	 *  
	 *  TODO: Support CIDR format (i.e. 192.168.1.0/24)
	 */
	public static IPV4Address getAddressFromString(String data) throws InvalidAddressFormatException{
		
		int net = 0;
		int mask = 0;
			
		String[] parts = data.split("[/]");
			
		//TODO Do better validations
		
		// If only an ip address if supplied, assume mask 255.255.255.255
		if (parts.length == 1){
			if (!pattern.matcher(parts[0]).matches())
				throw new InvalidAddressFormatException(data + " is not a valid IPV4 Address/Network Format");
			net = byteToInt(stringToByte(parts[0]));
			mask = byteToInt(stringToByte("255.255.255.255"));
		}
			
		// If both parts are supplied, assume first as ip and second as mask
		else if (parts.length == 2){
			if ((!pattern.matcher(parts[0]).matches()) || (!pattern.matcher(parts[0]).matches()))
				throw new InvalidAddressFormatException(data + " is not a valid IPV4 Address/Network Format");
			
			net = byteToInt(stringToByte(parts[0]));
			mask = byteToInt(stringToByte(parts[1]));
		}
			
		else{
			throw new InvalidAddressFormatException(data + " is not a valid IPV4 Address/Network Format");
		}
		
		return new IPV4Address(net, mask);
		
			
		
	}
	

	// Converts a 4 byte array representing an IP address
	// to a string of the format "www.xxx.yyy.zzz"
	private static String byteToString(byte[] src){
		return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff) + "." + (src[3] & 0xff);
	}
	
	// Converts a String representing an ip address
	// to a 4 byte array
	private static byte[] stringToByte(String sAddress){
		
		String[] octets = sAddress.split("[.]");
		
		byte[] bAddress = new byte[4];
		
		for (int i = 0; i < 4; i++){
			
			bAddress[i] = (byte) (Integer.parseInt(octets[i]) & 0xFF);
		}
		
		return bAddress;
		
	}
	
	// Converts a 4 byte array representing an IP address
	// to an integer
	private static int byteToInt(byte[] bAddress){
		
		int iAddress = 0;
		
		if (bAddress != null) {
			if (bAddress.length == 4) {
				iAddress  = bAddress[3] & 0xFF;
			  	iAddress |= ((bAddress[2] << 8) & 0xFF00);
			  	iAddress |= ((bAddress[1] << 16) & 0xFF0000);
			  	iAddress |= ((bAddress[0] << 24) & 0xFF000000);
			  }
		}
		return iAddress;

	}
	
	// Converts an int representing an ip address to
	// a 4 byte array.
	private static byte[] intToByte(int iAddress){
		byte[] bAddress = new byte[4];
		
		bAddress[0] = (byte) ((iAddress >>> 24) & 0xFF);
		bAddress[1] = (byte) ((iAddress >>> 16) & 0xFF);
		bAddress[2] = (byte) ((iAddress >>> 8) & 0xFF);
		bAddress[3] = (byte) (iAddress & 0xFF);
		
		return bAddress;
		
	}
	
	
}
