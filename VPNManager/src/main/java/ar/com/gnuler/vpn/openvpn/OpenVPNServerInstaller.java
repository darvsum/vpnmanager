package ar.com.gnuler.vpn.openvpn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.crypto.generators.DHParametersGenerator;
import org.bouncycastle.crypto.params.DHParameters;
import ar.com.gnuler.pki.DHParamPEMWriter;

public class OpenVPNServerInstaller extends Thread {

	//TODO pasar a un property file
	private static final String BASE_INSTANCE_DIR = "/tmp/openvpn/instances";
	Logger logger = Logger.getLogger("openvpn");
	
	
	/**
	 *  Interface to be implemented by those who want to be informed
	 *  on process status changes
	 */
	public interface OpenVPNServerInstallerListener extends EventListener {
	    void serverInstallationFinished(OpenVPNServerInstance server);
	}
	
	
	/**
	 * Listeners who need to be informed on the installation process
	 */
	private List<OpenVPNServerInstallerListener> listeners = new ArrayList<OpenVPNServerInstallerListener>();
	
	/**
	 */
	public enum Status {NOT_STARTED, GENERATING_DH, WRITING_CONFIG, INITIALIZING_LOG, FINISHED, FAILED};
	
		
	private Status status;
	private OpenVPNServerInstance server;
	
	/**
	 * Constructor
	 * 
	 * @param file Full path and name of where to save the generated dh param
	 */
	public OpenVPNServerInstaller(OpenVPNServerInstance server){
		this.server = server;
		status = Status.NOT_STARTED;
	}
	
	/**
	 * Constructor
	 * 
	 * @param file Full path and name of where to save the generated dh param
	 */
	public OpenVPNServerInstaller(OpenVPNServerInstance server, OpenVPNServerInstallerListener listener){
		this.server = server;
		status = Status.NOT_STARTED;
		listeners.add(listener);
	}
	
	@Override
	public void run() {
	
		
		logger.log(Level.WARNING, "Iniciando instalacion");
		
		try {
			
			createDir();
			
			
			status = Status.GENERATING_DH;
			logger.log(Level.INFO, "Generating DH Params");
			generateDH();
			logger.log(Level.INFO, "DH Params generated");
			
			logger.log(Level.INFO, "Initializing instance log file");
			status = Status.INITIALIZING_LOG;
			server.initializeLogFile();
			logger.log(Level.INFO, "Log file initialized");
			
			status = Status.WRITING_CONFIG;
			logger.log(Level.INFO, "Writing config file");
			server.writeConfigFile();
			logger.log(Level.INFO, "Config written");
			
			
		} catch (IOException e) {
			status = Status.FAILED;
			logger.log(Level.SEVERE, "Installation failed", e);
			//TODO: Do rollback (i.e. delete all created files and dirs)
			
			new File(server.getName()).delete(); // Esto es solo una simplificación del rollback
		}	
		
		logger.log(Level.INFO, "Installation finished successfully");
		status = Status.FINISHED;
		notifyProcessFinished();
		
	}
	
	public Status getStatus(){
		return status;
	}
	
	public void addDHParamGeneratorListener(OpenVPNServerInstallerListener listener){
		listeners.add(listener);
	}
	
	public void removeDHParamGeneratorListener(OpenVPNServerInstallerListener listener){
		listeners.remove(listener);
	}

	private void notifyProcessFinished(){
		for(OpenVPNServerInstallerListener listener: listeners){
			listener.serverInstallationFinished(server);
		}
	}

	private void generateDH() throws IOException {
		
		logger.log(Level.INFO, "Generating DH Params");
		
		// Generate DH Param
		DHParametersGenerator gen = new DHParametersGenerator();
		logger.log(Level.INFO, "Generating random number");
		SecureRandom srand = new SecureRandom();
		gen.init(512, 10, srand);
		logger.log(Level.INFO, "Generating paramenters");
		DHParameters params = gen.generateParameters();
		DHParameter  param = new DHParameter(params.getP(), params.getG(), params.getL());
		logger.log(Level.INFO, "Writing DH to file " + server.getDHParamPath());
		// Write it to disk
		File file = new File(server.getDHParamPath());
		DHParamPEMWriter a = new DHParamPEMWriter(new OutputStreamWriter(new FileOutputStream(file)));
		a.writeObject(param);
		a.close();
		
	}
	
	private void createDir() throws IOException{
		
		logger.log(Level.INFO, "Creating directory");
		boolean ok = new File(BASE_INSTANCE_DIR + "/" + server.getName()).mkdir();
	
		if (!ok){
			logger.log(Level.SEVERE, "Directory creation failed");
			throw new IOException("Failed to create dir '" + server.getName() + "'.");
		}
		logger.log(Level.INFO, "Directory creation successfull");
	}
	
}
