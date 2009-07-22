package ar.com.gnuler.pki;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.x500.X500Principal;

public class IdentityStore {
	
	// Singleton
	private static IdentityStore INSTANCE = null;
	
	private KeyStore store;
	
	
	public IdentityStore(){
		
		try {
			
			store = KeyStore.getInstance("PKCS12", "BC");
			store.load(null, null);
			
		} catch (Exception e) {
			System.out.println("Unable to initalize Identity Store: " + e.toString());
		}
		
	}
	
	public void addCertificate(String alias, X509Certificate cert) throws KeyStoreException{
		store.setCertificateEntry(alias,cert);
	}
	
	
	/**
	 * Adds a certificate with it's corresponding private key to the store.
	 * WARNING: Till now the key is NOT protected with any password.
	 * 
	 * @param alias
	 * @param key
	 * @param chain 
	 * @throws KeyStoreException
	 */
	public void addCertificateAndKey(String alias, Key key, X509Certificate[] chain ) throws KeyStoreException{
		store.setKeyEntry(alias, key, null, chain); //TODO no password by now.
	}
	
	
	/**
	 * Adds a certificate with it's correspondig private key to the store.
	 * The CA must be previously present on the store.
	 * 
	 * @param alias
	 * @param key
	 * @param cert
	 * @throws KeyStoreException 
	 */
	public void addCertificateAndKey(String alias, Key key, X509Certificate serverCert) throws KeyStoreException{
		
		// Look up for the issuer cert
		X509Certificate caCert = getCertificateForDN(serverCert.getIssuerX500Principal());
		
		System.out.println("---------------------");
		System.out.println(caCert.toString());
		System.out.println("---------------------");
		
		X509Certificate[] chain = new X509Certificate[2];
		chain[0] = serverCert;
		chain[1] = caCert;
		
		addCertificateAndKey(alias, key, chain);
		
	}
	
	
	
	public X509Certificate getCertificateForDN(X500Principal subjectRDN){
		
		try {
			
			for (Enumeration<String> e = store.aliases(); e.hasMoreElements();) {
				
				String alias = e.nextElement();

				X509Certificate cert = (X509Certificate) store.getCertificate(alias);
						
				if (cert.getSubjectX500Principal().equals(subjectRDN)){
					return cert;
				}
				  
			}
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	
	public X509Certificate getCertificateForAlias(String alias) throws KeyStoreException{
		return (X509Certificate) store.getCertificate(alias);
	}
	
	public void showContent(){
		 Enumeration<String> en;
		try {
			en = store.aliases();
			while (en.hasMoreElements())
		    {
		          String alias = (String)en.nextElement();
		          System.out.println("found " + alias + ", isCertificate? " + store.isCertificateEntry(alias));
		    }
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	}
	
	public List<String> getAliases() throws KeyStoreException{
		
		return Collections.list(store.aliases());
		
	}
	
	public static IdentityStore getInstance(){
		if (INSTANCE == null)
			INSTANCE = new IdentityStore();
		
		return INSTANCE;
	}
}
