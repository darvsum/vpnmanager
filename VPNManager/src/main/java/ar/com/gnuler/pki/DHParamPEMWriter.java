package ar.com.gnuler.pki;

import java.io.IOException;
import java.io.Writer;

import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.encoders.Base64;


/*
 * DHParamPEMWriter is an extension of PEMWriter which
 * adds the capability to Write DHParams to files in
 * PEM Format. 
 */

public class DHParamPEMWriter extends PEMWriter{

	public DHParamPEMWriter(Writer arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
	
   public void writeObject(Object  obj) throws IOException {
	
	   String  type;
	   byte[]  encoding;
	            
	   if (obj instanceof DHParameter){
	   		type = "DH PARAMETERS";
	   		
	   		DHParameter dhParam = (DHParameter) obj;
	   		encoding = dhParam.getEncoded();
	   		writeHeader(type);
	   		writeEncoded(encoding);
	   		writeFooter(type);

	   }else
		   super.writeObject(obj);
	   		

   }

   /* This method is _the same_ that the one with the same
    * same in the father class, exactly the same.
    * 
    * Unfortunately bouncycastle developers didn't make
    * the method public or protected, which forced me to
    * copy-paste from theirs... sory :s
    */
   
   private void writeEncoded(byte[] bytes) throws IOException {
	   char[]  buf = new char[64];
            
       bytes = Base64.encode(bytes);
            
       for (int i = 0; i < bytes.length; i += buf.length)
       {
    	   int index = 0;
                
           while (index != buf.length)
           {
        	   if ((i + index) >= bytes.length)
               {
        		   break;
        	   }
               buf[index] = (char)bytes[i + index];
               index++;
           }
           this.write(buf, 0, index);
           this.newLine();
           
    	}
	}


   /* This method is _the same_ that the one with the same
    * same in the father class, exactly the same.
    * 
    * Unfortunately bouncycastle developers didn't make
    * the method public or protected, which forced me to
    * copy-paste from theirs... sory :s
    */
   private void writeHeader(String type) throws IOException {
	   this.write("-----BEGIN " + type + "-----");
	   this.newLine();
   }
   
   /* This method is _the same_ that the one with the same
    * same in the father class, exactly the same.
    * 
    * Unfortunately bouncycastle developers didn't make
    * the method public or protected, which forced me to
    * copy-paste from theirs... sory :s
    */
    private void writeFooter(String type) throws IOException {
    	this.write("-----END " + type + "-----");
    	this.newLine();
    }


}