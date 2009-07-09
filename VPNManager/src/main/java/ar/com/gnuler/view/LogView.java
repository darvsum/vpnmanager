package ar.com.gnuler.view;

import java.io.IOException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.time.Duration;
import ar.com.gnuler.util.LogTailer;
import ar.com.gnuler.util.LogTailer.LogUpdateListener;

public class LogView extends WebPage{
	
	ListView<String> listView;
	
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
	
	public LogView(PageParameters parameters){
		
		LogTailer lt = null;
		
		String file = parameters.getString("f");
		
		
		LastMessageModel lastMessageModel = new LastMessageModel();
		
		try {
			 lt = new LogTailer(file, lastMessageModel);
			 lt.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		MultiLineLabel myLabel = new MultiLineLabel("lastmessage", lastMessageModel);
        add(myLabel);
        myLabel.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
        myLabel.setOutputMarkupId(true);
        
	    
	}

	
}
