package ar.com.gnuler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.bouncycastle.openssl.PEMReader;

public class PKITest {

	
	public PKITest(){
		

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {

			// Leer un certificado en formato PEM
			X509Certificate cert = readX509CertificateFromPEMFile(new File("server.crt"));
			System.out.println(cert.toString());
	
			
			// Leer un KeyPair en formato PEM
			KeyPair keyPair = readKeyPairFromPEMFile(new File("server.key"));
			System.out.println(keyPair.getPrivate().toString());
			System.out.println(keyPair.getPublic().toString());
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	private static X509Certificate readX509CertificateFromPEMFile(File file) throws IOException{
		
		X509Certificate cert = null;
		PEMReader pemReader = null;
		
		pemReader = new PEMReader(new FileReader(file));
		cert = (X509Certificate)pemReader.readObject();

		return cert;
	
		
	}
	
	private KeyPair readKeyPairFromPEMFile(File file) throws IOException{
		
		KeyPair keyPair = null;
		PEMReader pemReader = null;
		pemReader = new PEMReader(new FileReader(file));
		keyPair = (KeyPair)pemReader.readObject();
		
		return keyPair;

	}
	
	
	public static void main(String[] args){
		
		 new PKITest();
		
	}
	
}
