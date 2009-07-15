package ar.com.gnuler.view;

import java.io.IOException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;
import ar.com.gnuler.util.LogTailer;
import ar.com.gnuler.util.LogTailer.LogUpdateListener;

public class LogPanel extends Panel{
	
	private static final long serialVersionUID = -7198798871681372196L;
	ListView<String> listView;
	LogTailer lt = null;
	String fileName;
	LastMessageModel lastMessageModel;
	MultiLineLabel myLabel;
	AjaxSelfUpdatingTimerBehavior logUpdateBehavior;
	
	 private static class LastMessageModel extends AbstractReadOnlyModel<String> implements LogUpdateListener {
	 
		private static final long serialVersionUID = 1L;
		
		String messages = "";

	        public LastMessageModel()
	        {	         
	        }

	        /**
	         * @see org.apache.wicket.model.AbstractReadOnlyModel#getObject()
	         */
	        @Override
	        public String getObject()
	        {
	            return messages;
	        }
	        
	        public void logUpdated(String line) {
	    		messages +=  line + "\n";
	    	}

	    	public void messageUpdated(String message) {
	    		messages +=  message + "\n";
	    	}
	 }
	
	public LogPanel(String id, String file){
		super(id);
		
		this.fileName = file;
		
		lastMessageModel = new LastMessageModel();
		logUpdateBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1));
	

		
			
		try {
			 lt = new LogTailer(file, lastMessageModel);
			 lt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		

		myLabel = new MultiLineLabel("lastmessage", lastMessageModel){
			
			@Override
			protected void onAfterRender() {
				super.onAfterRender();
				if (LogPanel.this.getMonitorLog())
					getResponse().write( "<script>div=document.getElementById('logmessages'); div.scrollTop = div.scrollHeight;</script>" );
			}

			private static final long serialVersionUID = 1L;
			
		};
		

        add(myLabel);
        myLabel.setOutputMarkupId(true);
      myLabel.add(logUpdateBehavior);        
        
        
        
        
        add(new AjaxCheckBox("enablelog", new PropertyModel<Boolean>(this, "monitorLog")){

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				
				

				
				//Do nothing, the model does it for us
				if (target != null)
					target.addComponent(myLabel);
			}
        	
        });
        
        
	    
	}
	
	public void setMonitorLog(boolean monitorLog) throws IOException{
				
		// If you want to monitor the log
		if (monitorLog){
			
			// First we assure that there is no instance running
			if (lt != null){
				if (lt.isAlive())
					lt.stopThread();
				lt = null;
			}
			
			lt = new LogTailer(fileName, lastMessageModel);
			lt.start();
			
		}
		
		// If you want to stop monitoring...
		else{
			if (lt.isAlive())
				lt.stopThread();
			lt = null;	
		}
		
		
	}
	
	public boolean getMonitorLog(){
		if (lt == null)
			return false;
		else if (lt.isAlive())
			return true;
		
		return false;
	}

	
}
