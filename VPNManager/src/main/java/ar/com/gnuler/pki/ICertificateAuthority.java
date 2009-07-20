package ar.com.gnuler.pki;


import java.security.cert.X509Certificate;
import java.util.List;

public interface ICertificateAuthority {
	
	/**
	 * Returns the name of the CA. The name is only an identifier and
	 * is not necessary to be the same as the CN.
	 * @return
	 */
	public String getName();
	
	
	/**
	 * Imports a certificate from a file in PEM format
	 * 
	 * The certificate MUST be a child of this CA, other way will
	 * not be imported. (TODO ¿tirar una excepción en este caso?) 
	 * 
	 * @param fileName File containing a certificate in PEM format.
	 */
	public void importPEMCertificate(String fileName);
	
	
	/**
	 * Adds a certificate to the CA.
	 * 
	 * The certificate MUST be a child of this CA, other way will
	 * not be imported. (TODO ¿tirar una excepción en este caso?) 

	 * @param cert The Certificate to add
	 */
	public void add(GenericCertificate cert);
	
	/**
	 * Returns the certificates associated with this CA
	 * @return Certificates associated with this CA
	 */
	public List<GenericCertificate> getCertificates();
	
	
	/**
	 * Exports the CA Certificate to the file in PEM format
	 * @param fileName where to save the certificate in PEM format
	 */
	public void exportPEMCert(String fileName);
	
}
