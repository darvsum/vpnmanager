package ar.com.gnuler.pki;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class CertificateStore {
	
	private static CertificateStore INSTANCE = null;
	private List<X509Certificate> caCerts = new ArrayList<X509Certificate>();
	private List<X509Certificate> userCerts = new ArrayList<X509Certificate>();
	private List<X509Certificate> serverCerts = new ArrayList<X509Certificate>();

	
	public CertificateStore(){
		

	}
	
	
	public void addUserCert(X509Certificate cert){
		userCerts.add(cert);
	}
	
	public void addUserCert(String fileName){
		userCerts.add(readFromFile(fileName));
	}
	
	public List<X509Certificate> getUserCerts(){
		return userCerts;
	}
	
	
	
	public void addServerCert(X509Certificate cert){
		serverCerts.add(cert);
	}
	
	public void addServerCert(String fileName){
		serverCerts.add(readFromFile(fileName));
	}
	
	public List<X509Certificate> getServerCerts(){
		return userCerts;
	}
	
	
	
	public void addCACert(X509Certificate cert){
		caCerts.add(cert);
	}
	
	public void addCACert(String fileName){
		caCerts.add(readFromFile(fileName));
	}
		
	
	public List<X509Certificate> getCACerts(){
		return userCerts;
	}
	
	
	
	private X509Certificate readFromFile(String fileName){
		X509Certificate cert = null;
		
		InputStream inStream;
		try {
			inStream = new FileInputStream("C:/oscar/Proyectos/OCSP/veri_viabcp.cer");
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate)cf.generateCertificate(inStream);
			inStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cert;
	
		
	}
	
	public static CertificateStore getInstance(){
		if (INSTANCE == null)
			INSTANCE = new CertificateStore();
		
		return INSTANCE;
	}
	
	
	
}
