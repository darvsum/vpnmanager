package ar.com.gnuler.pki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.openssl.PEMReader;

public class Utils {
	
    /**
     * Create a random 1024 bit RSA key pair
     * @return 1024 RSA keypair
     */
    public static KeyPair generateRSAKeyPair()
        throws Exception
	{
        KeyPairGenerator  kpGen = KeyPairGenerator.getInstance("RSA", "BC");
    
        kpGen.initialize(1024, new SecureRandom());
    
        return kpGen.generateKeyPair();
	}
    

	public static X509Certificate readX509CertificateFromPEMFile(File file) throws IOException{
		
		X509Certificate cert = null;
		PEMReader pemReader = null;
		
		pemReader = new PEMReader(new FileReader(file));
		cert = (X509Certificate)pemReader.readObject();

		return cert;
	
		
	}
	
	public static KeyPair readKeyPairFromPEMFile(File file) throws IOException{
		
		KeyPair keyPair = null;
		PEMReader pemReader = null;
		pemReader = new PEMReader(new FileReader(file));
		keyPair = (KeyPair)pemReader.readObject();
		
		return keyPair;

	}
	
}
