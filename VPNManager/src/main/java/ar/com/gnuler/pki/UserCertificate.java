package ar.com.gnuler.pki;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

public class UserCertificate {
	private X509Certificate cert;
	private X509Certificate rootCert;
	private KeyPair pair;
	
	public UserCertificate(
			X509Certificate cert,
			X509Certificate rootCert,
			KeyPair pair){
	
		this.cert = cert;
		this.rootCert = rootCert;
		this.pair = pair;
		
	}

}
