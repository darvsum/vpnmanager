package ar.com.gnuler.pki;

public interface ICertificateAuthority {
	public UserCertificate createUserCert(String DN, String Mail) throws Exception;
	public void createServerCert(String DN, String Mail);
	public String getName();
}
