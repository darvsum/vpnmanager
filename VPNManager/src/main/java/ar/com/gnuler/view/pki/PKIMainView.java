package ar.com.gnuler.view.pki;

import java.io.CharArrayReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.bouncycastle.openssl.PEMReader;

import ar.com.gnuler.pki.CertificateAuthorityFactory;
import ar.com.gnuler.pki.IdentityStore;
import ar.com.gnuler.view.ViewTemplate;
import ar.com.gnuler.view.components.OpenVPNServersList;

public class PKIMainView extends ViewTemplate {
	
	
	/*
    * Form for uploads.
    */
   private class CertImportForm extends Form<Void>
   {
       private FileUploadField fileUploadField;

       /**
        * Construct.
        * 
        * @param name
        *            Component name
        */
       public CertImportForm(String name)
       {
           super(name);

           // set this form to multipart mode (allways needed for uploads!)
           setMultiPart(true);

           // Add one file input field
           add(fileUploadField = new FileUploadField("fileInput"));

           // Set maximum size to 100K for demo purposes
           setMaxSize(Bytes.kilobytes(100));
       }

       /**
        * @see org.apache.wicket.markup.html.form.Form#onSubmit()
        */
       @Override
       protected void onSubmit()
       {
           final FileUpload upload = fileUploadField.getFileUpload();
           if (upload != null)
           {
               // Create a new file
        	   
        	   
        	   
        	   X509Certificate cert = null;
       			PEMReader pemReader = null;
       		
       			
       			
       			InputStreamReader b;
				try {
					
					b = new InputStreamReader(upload.getInputStream());
					pemReader = new PEMReader(b);
					cert = (X509Certificate)pemReader.readObject();
//					System.out.println(cert.toString());
					
					IdentityStore.getInstance().addCertificate("test", cert);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (KeyStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

        	   
           }
       }
   }
   
//   CertAndKeyImportFormForm
   

	/*
   * Form for uploads.
   */
  private class CertAndKeyImportFormForm extends Form<Void>
  {
      private FileUploadField fileUploadField1;
      private FileUploadField fileUploadField2;

      /**
       * Construct.
       * 
       * @param name
       *            Component name
       */
      public CertAndKeyImportFormForm(String name)
      {
          super(name);

          // set this form to multipart mode (allways needed for uploads!)
          setMultiPart(true);

          // Add one file input field
          add(fileUploadField1 = new FileUploadField("fileInput1"));
          add(fileUploadField2 = new FileUploadField("fileInput2"));

          // Set maximum size to 100K for demo purposes
          setMaxSize(Bytes.kilobytes(100));
      }

      /**
       * @see org.apache.wicket.markup.html.form.Form#onSubmit()
       */
      @Override
      protected void onSubmit()
      {
          final FileUpload upload1 = fileUploadField1.getFileUpload();
          final FileUpload upload2 = fileUploadField2.getFileUpload();
          
          if ((upload1 != null) && (upload1 != null))
          {
              // Create a new file
       	   
       	   
       	   
       	   		X509Certificate cert = null;
       	   	    KeyPair keyPair = null;
      			PEMReader pemReader = null;
      		
      			
      			
      			InputStreamReader b;
				try {
					
					// Leo el certificado
					pemReader = new PEMReader(new InputStreamReader(upload1.getInputStream()));
					cert = (X509Certificate)pemReader.readObject();
					
					// Leo el par de llaves
					pemReader = new PEMReader(new InputStreamReader(upload2.getInputStream()));
					keyPair = (KeyPair)pemReader.readObject();
					
					// Agrego el par al store
					IdentityStore.getInstance().addCertificateAndKey("test2", keyPair.getPrivate(), cert);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (KeyStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
          }
      }
  }
  
	
	private WebMarkupContainer container;
	
	public PKIMainView(){
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(new CertificatesList("servers", container));
		
		add(container);
		
		final CertImportForm simpleUploadForm = new CertImportForm("importcert");
	    add(simpleUploadForm);
		
	    final CertAndKeyImportFormForm form2 = new CertAndKeyImportFormForm("importcertandkey");
	    add(form2);
	    
	}
	
	  /**
     * Check whether the file allready exists, and if so, try to delete it.
     * 
     * @param newFile
     *            the file to check
     */
    private void checkFileExists(File newFile)
    {
        if (newFile.exists())
        {
            // Try to delete the file
            if (!Files.remove(newFile))
            {
                throw new IllegalStateException("Unable to overwrite " + newFile.getAbsolutePath());
            }
        }
    }

    private Folder getUploadFolder()
    {
        return new Folder("/tmp/openvpn/certs/");
    }
}
