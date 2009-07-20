package ar.com.gnuler.pki;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500PrivateCredential;

public class GenericCertificate {
	private X509Certificate cert;
	private KeyPair pair;
	
	
	public GenericCertificate(
			X509Certificate cert,
			KeyPair pair){
	
		this.cert = cert;
		this.pair = pair;
		
		X500PrivateCredential a;
		
		
	}
	
	public GenericCertificate(
			X509Certificate cert,
			X509Certificate rootCert,
			KeyPair pair){
	
		this.cert = cert;
		
		this.pair = pair;
		
	}


}
