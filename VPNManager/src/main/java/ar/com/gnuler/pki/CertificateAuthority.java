package ar.com.gnuler.pki;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.Attribute;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

public class CertificateAuthority implements ICertificateAuthority, Serializable{

	private static final long serialVersionUID = 1869081843311360477L;
	X509Certificate rootCert;
	KeyPair rootPair;
	
	private String name;
	
	private List<X509Certificate> certs = new ArrayList<X509Certificate>();
	
	public CertificateAuthority(X509Certificate rootCert, KeyPair rootPair, String name){
		this.rootCert = rootCert;
		this.rootPair = rootPair;
		this.name = name;
	}
	
	public void createServerCert(String DN, String Mail) {
		// TODO Auto-generated method stub
		
	}
	
	public String getName(){
		return name;
	}

	@SuppressWarnings("unchecked")
	public UserCertificate createUserCert(String DN, String Mail) throws Exception {
		 // create the certification request
        KeyPair          pair = Utils.generateRSAKeyPair();
        
        PKCS10CertificationRequest  request = generateRequest(pair);
        
        // validate the certification request
        if (!request.verify("BC"))
        {
            System.out.println("request failed to verify!");
            System.exit(1);
        }
        
        // create the certificate using the information in the request
        X509V3CertificateGenerator  certGen = new X509V3CertificateGenerator();

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(rootCert.getSubjectX500Principal());
        certGen.setNotBefore(new Date(System.currentTimeMillis()));
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 50000));
        certGen.setSubjectDN(request.getCertificationRequestInfo().getSubject());
        certGen.setPublicKey(request.getPublicKey("BC"));
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
        
        certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false, new AuthorityKeyIdentifierStructure(rootCert));
        
        certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false, new SubjectKeyIdentifierStructure(request.getPublicKey("BC")));
        
        certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
        
        certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        
        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
        
        // extract the extension request attribute
        ASN1Set attributes = request.getCertificationRequestInfo().getAttributes();
        
        for (int i = 0; i != attributes.size(); i++)
        {
            Attribute    attr = Attribute.getInstance(attributes.getObjectAt(i));
            
            // process extension request
            if (attr.getAttrType().equals(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest))
            {
                X509Extensions extensions = X509Extensions.getInstance(attr.getAttrValues().getObjectAt(0));
                
                Enumeration<DERObjectIdentifier> e = extensions.oids();
                while (e.hasMoreElements())
                {
                    DERObjectIdentifier oid = e.nextElement();
                    X509Extension       ext = extensions.getExtension(oid);
                    
                    certGen.addExtension(oid, ext.isCritical(), ext.getValue().getOctets());
                }
            }
        }
        
        X509Certificate  issuedCert = certGen.generate(rootPair.getPrivate());
        
        return new UserCertificate(issuedCert, rootCert, pair);
		
	}

    private PKCS10CertificationRequest generateRequest(
            KeyPair pair)
            throws Exception
        {
            // create a SubjectAlternativeName extension value
            GeneralNames  subjectAltNames = new GeneralNames(
                     new GeneralName(GeneralName.rfc822Name, "test@test.test"));

            // create the extensions object and add it as an attribute
            Vector<DERObjectIdentifier>  oids = new Vector<DERObjectIdentifier>();
            Vector<X509Extension>	values = new Vector<X509Extension>();

            oids.add(X509Extensions.SubjectAlternativeName);
            values.add(new X509Extension(false, new DEROctetString(subjectAltNames)));
            
            X509Extensions	extensions = new X509Extensions(oids, values);
            
            Attribute  attribute = new Attribute(
                                     PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, 
                                     new DERSet(extensions));
            
            return new PKCS10CertificationRequest(
                    "SHA256withRSA",
                    new X500Principal("CN=Requested Test Certificate"),
                    pair.getPublic(),
                    new DERSet(attribute),
                    pair.getPrivate());
        }

	
	public List<X509Certificate> getCertificates(){
		return this.certs;
	}
	
	
	public void add(X509Certificate cert){
		this.add(cert);
	}
    
}
