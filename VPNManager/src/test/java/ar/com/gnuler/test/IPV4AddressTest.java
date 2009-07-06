package ar.com.gnuler.test;

import ar.com.gnuler.net.IPV4Address;
import ar.com.gnuler.net.InvalidAddressFormatException;
import junit.framework.TestCase;

public class IPV4AddressTest extends TestCase {
	
	
	public void testSimpleHostAddress(){
		
		String ip = "192.168.1.1";
		String mask = "255.255.255.255";
		
		IPV4Address address;
		try {
			address = IPV4Address.getAddressFromString(ip);
			assertEquals(ip, address.getStringNet());
			assertEquals(mask, address.getStringMask());
			assertTrue(address.isHost());
		} catch (InvalidAddressFormatException e) {
			fail();
		}
	
		
		
	}
	
	
	public void testSubnet(){
		
		String ip = "10.10.1.2";
		String mask = "255.255.0.0";
		String all = ip + "/" + mask;
		
		IPV4Address address;
		try {	
			address = IPV4Address.getAddressFromString(all);
			assertEquals(ip, address.getStringNet());
			assertEquals(mask, address.getStringMask());
			assertTrue(!address.isHost());
			
		} catch (InvalidAddressFormatException e) {
			fail();
		}
		
		
	}
	
	
	
	
	
	
	
}
