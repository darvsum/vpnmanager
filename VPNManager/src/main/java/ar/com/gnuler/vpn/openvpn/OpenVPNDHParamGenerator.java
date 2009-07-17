package ar.com.gnuler.vpn.openvpn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.crypto.generators.DHParametersGenerator;
import org.bouncycastle.crypto.params.DHParameters;
import ar.com.gnuler.pki.DHParamPEMWriter;

public class OpenVPNDHParamGenerator extends Thread {

	/**
	 *  Interface to be implemented by those who want to be informed
	 *  on process status changes
	 */
	public interface DHParamGeneratorListener extends EventListener {
	    void dhGenerationProcessFinished(String id);
	}
	
	
	/**
	 * Listeners who need to be informed on the DH generation process
	 */
	private List<DHParamGeneratorListener> listeners = new ArrayList<DHParamGeneratorListener>();
	
	/**
	 *  Posible Generator status
	 * 
	 *  - NOT_STARTED: Status when the object is recently created and
	 *                 not started.
	 *  - GENERATING : The generation process is on the go
	 *  - GENERATED  : The process finished succesfully.
	 *  - FAILED     : The process finished but the DH Parameters was not generated
	 */
	public enum Status {NOT_STARTED, GENERATING, GENERATED, FAILED};
	
	
	/** 
	 *  File where to save the generated dh param
	 */
	private String fileName;
	
	/**
	 * Current status
	 */
	private Status status;
	
	/**
	 * ServerName for whom is the DHParam
	 */
	private String serverName;
	
	/**
	 * Constructor
	 * 
	 * @param file Full path and name of where to save the generated dh param
	 */
	public OpenVPNDHParamGenerator(String file, String serverName){
		this.fileName = file;
		status = Status.NOT_STARTED;
		this.serverName = serverName;
	}
	
	/**
	 * Constructor
	 * 
	 * @param file Full path and name of where to save the generated dh param
	 */
	public OpenVPNDHParamGenerator(String file, String serverName, DHParamGeneratorListener listener){
		this.fileName = file;
		status = Status.NOT_STARTED;
		this.serverName = serverName;
		listeners.add(listener);
	}
	
	public Status getStatus(){
		return status;
	}
	
	@Override
	public void run() {
	
		status = Status.GENERATING;
		
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
			
			System.out.println("-------------------1");
			File file = new File(fileName);
			DHParamPEMWriter a = new DHParamPEMWriter(new OutputStreamWriter(new FileOutputStream(file)));
			a.writeObject(param);
			System.out.println("-------------------3");
			a.close();
			
			status = Status.GENERATED;
			
			System.out.println("Generated :)");
			notifyProcessFinished();
			
		} catch (IOException e) {
			status = Status.FAILED;
		}
		


	}
	
	public void addDHParamGeneratorListener(DHParamGeneratorListener listener){
		listeners.add(listener);
	}
	
	public void removeDHParamGeneratorListener(DHParamGeneratorListener listener){
		listeners.remove(listener);
	}

	private void notifyProcessFinished(){
		for(DHParamGeneratorListener listener: listeners){
			listener.dhGenerationProcessFinished(serverName);
		}
	}

}
