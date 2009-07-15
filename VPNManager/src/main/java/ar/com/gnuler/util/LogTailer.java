package ar.com.gnuler.util;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

//Adapted from JLogTailer
// http://www.jibble.org/jlogtailer.php
public class LogTailer extends Thread implements Serializable {

	private static final long serialVersionUID = 8540998139728302348L;
	private boolean running;
	private int updateInterval = 1000;
    private File file;
    private long filePointer;
    private List<String> messages = new LinkedList<String>();
    private List<String> log = new LinkedList<String>();
	private LogUpdateListener listener;
	
    public interface LogUpdateListener extends EventListener{
    	public void logUpdated(String line);
    	public void messageUpdated(String message);
    }
    
    public LogTailer(String filePath, LogUpdateListener listener) throws IOException{
    	
    	this.listener = listener;
    	this.file = new File(filePath);
    	
        // Do not allow tail logging of non-existant files. (Is this a good idea?)
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            throw new IOException("Can't read this file.");
        }
        
        
        filePointer = file.length();

        // Start from 500 character from the final
        if (filePointer > 500)
        	filePointer -= 500;
        else
        	filePointer = 0;
        
        
        this.appendMessage("Log tailing started on " + file.toString());
    }
    
    private void appendMessage(String message){
    	messages.add(message);
    	listener.messageUpdated(message);
    }
    
    private void appendLine(String line){
    	log.add(line);
    	listener.logUpdated(line);
    }
    
    public List<String> getMessages(){
    	return this.messages;
    }
    
    public List<String> getLog(){
    	return this.log;
    }
    
    public void stopThread(){
    	this.running = false;
    }
    
	public void run() {
		running = true;
        System.out.println("Aca estamos... ");
		
        try {
            while (running) {
                Thread.sleep(updateInterval);
                long len = file.length();
                if (len < filePointer) {
                    // Log must have been jibbled or deleted.
                    this.appendMessage("Log file was reset. Restarting logging from start of file.");
                    filePointer = len;
                }
                else if (len > filePointer) {
                    // File must have had something added to it!
                    RandomAccessFile raf = new RandomAccessFile(file, "r");
                    raf.seek(filePointer);
                    String line = null;
                    while ((line = raf.readLine()) != null) {
                        this.appendLine(line);
                    }
                    filePointer = raf.getFilePointer();
                    raf.close();
                }
            }
            this.appendMessage("Log tail stopped.");

        }
        catch (Exception e) {
            this.appendMessage("Fatal error reading log file, log tailing has stopped.");
        }
        // dispose();
    }
}
