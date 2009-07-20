package ar.com.gnuler.pki;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

public class ServerCertificate extends GenericCertificate {
	
	
	public ServerCertificate(
			X509Certificate cert,
			X509Certificate rootCert,
			KeyPair pair){
		super(cert, rootCert, pair);
		
	}

}
