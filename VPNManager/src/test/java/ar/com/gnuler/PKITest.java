package ar.com.gnuler;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.cert.X509Certificate;

import ar.com.gnuler.pki.IdentityStore;
import ar.com.gnuler.pki.Utils;

public class PKITest {

	
	public PKITest(){
		

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {

			// Leer un certificado en formato PEM (CA)
			X509Certificate caCert = Utils.readX509CertificateFromPEMFile(new File("ca.crt"));
			
			// Leer un certificado en formato PEM (Server)
			X509Certificate serverCert = Utils.readX509CertificateFromPEMFile(new File("server.crt"));
	
			
			// Leer un KeyPair en formato PEM
			KeyPair serverKeyPair = Utils.readKeyPairFromPEMFile(new File("server.key"));

			IdentityStore myIdStore = IdentityStore.getInstance();
			
			
			myIdStore.addCertificate("ca", caCert);
			myIdStore.addCertificate("serverCert", serverCert);
			
			
			
			myIdStore.addCertificateAndKey("serverCertconKey", serverKeyPair.getPrivate(),  serverCert);
			
			myIdStore.showContent();
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	
	
	public static void main(String[] args){
		
		 new PKITest();
		
	}
	
}
