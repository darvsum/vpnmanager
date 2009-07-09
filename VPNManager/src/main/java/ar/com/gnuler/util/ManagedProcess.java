package ar.com.gnuler.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ManagedProcess extends Thread{
	private Process process;
	private String cmd;
	private String id;
	private List<ProcessListener> listeners = new ArrayList<ProcessListener>();
	
	// Interface to be implemented by those who want to be informed
	// on process status changes
	public interface ProcessListener extends EventListener {
	    void processFinished(String id, ManagedProcess process);
	}

	
	public ManagedProcess(String id, String cmd){
		this.cmd = cmd;
		this.id = id;
	}

	
	public void addProcessListener(ProcessListener listener){
		listeners.add(listener);
	}
	
	public void removeProcessListener(ProcessListener listener){
		listeners.remove(listener);
	}
	
	public void killProcess(){
		process.destroy();
	}
	
	private void notifyProcessFinished(){
		for(ProcessListener listener: listeners){
			listener.processFinished(id, this);
		}
	}
	
	@Override
	public void run() {
		try {
			//Run the process
			process = Runtime.getRuntime().exec(cmd);
			
			// Wait for the process to finish
			process.waitFor();
			
			notifyProcessFinished();
			
		} catch (IOException e) {
			//TODO view this...
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
