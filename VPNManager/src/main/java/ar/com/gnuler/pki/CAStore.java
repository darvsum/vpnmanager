package ar.com.gnuler.pki;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

public class CAStore {
	
	private static CAStore INSTANCE = null;
	private List<ICertificateAuthority> cas = new ArrayList<ICertificateAuthority>();

	
	public CAStore(){
	
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//TODO creo dos CA's para test
		ICertificateAuthority ca1;
		ICertificateAuthority ca2;
		
//		try {
//			ca1 = CertificateAuthorityFactory.createSelfSignedCA("Gnuler Root CA", "GnulerRootCA", "ca@gnuler.com.ar");
//			CAStore.getInstance().addCA(ca1);
//			
//			ca2 = CertificateAuthorityFactory.createSelfSignedCA("GCBA Root CA", "GCBA Root CA", "ca@buenosaires.gov.ar");
//			CAStore.getInstance().addCA(ca2);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		

	}
	
	public List<ICertificateAuthority> getCAs(){
		return this.cas;
	}
	
	
	public List<String> getCaNames(){
		List<String> names = new ArrayList<String>();
		
		
		for (ICertificateAuthority ca: cas){
			names.add(ca.getName());
		}
		
		return names;
	}
	
	
	public void addCA(ICertificateAuthority ca){
		cas.add(ca);
	}
	
	
	
	public static CAStore getInstance(){
		if (INSTANCE == null)
			INSTANCE = new CAStore();
		
		return INSTANCE;
	}
	
	
	
}
