package ar.com.gnuler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.crypto.generators.DHParametersGenerator;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.openssl.PEMWriter;

import ar.com.gnuler.pki.CAStore;
import ar.com.gnuler.pki.CertificateAuthorityFactory;
import ar.com.gnuler.pki.DHParamPEMWriter;
import ar.com.gnuler.pki.ICertificateAuthority;

public class PKITest {

	
	
	public static void main(String[] args){
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		
		 System.out.println("Generating 1...");
		 DHParametersGenerator gen = new DHParametersGenerator();
		 System.out.println("Generating 2...");
		 SecureRandom srand = new SecureRandom();
		 System.out.println("Generating 3...");
		 gen.init(512, 10, srand);
		 System.out.println("Generating 4...");
		 
		 DHParameters params = gen.generateParameters();
		 System.out.println("Generating 5...");
		 DHParameter  param = new DHParameter(params.getP(), params.getG(), params.getL());
		 System.out.println("Generating 6...");
		 
		 
		 
		 try {
			System.out.println(param.getEncoded(DHParameter.DER));
			
			System.out.println(param.getDEREncoded());
			
			System.out.println("-------------------1");
			DHParamPEMWriter a = new DHParamPEMWriter(new OutputStreamWriter(System.out));
			a.writeObject(param);
			System.out.println("-------------------2");
			
			System.out.println("-------------------3");
			a.close();
//			
//			PEMWriter        pemWrt = new PEMWriter(new OutputStreamWriter(System.out));
////	        pemWrt.writeObject(param.getEncoded(DHParameter.DER));
//	        pemWrt.writeObject(param.getDERObject());
//	        pemWrt.close();

			
			
			
			System.out.println("Generated :)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 
		
	}
	
}
