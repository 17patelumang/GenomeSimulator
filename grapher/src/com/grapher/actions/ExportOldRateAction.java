package com.grapher.actions;

/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */



import java.io.File;
import java.io.FileWriter;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.grapher.View;
import com.grapher.ui.GenerationGraph;
import com.grapher.ui.MyTitleAreaDialog;

public class ExportOldRateAction extends Action {
	
    public ExportOldRateAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.exportoldrate");
    }
    
    public void run() {

    	try{
        	FileDialog dialog =  new FileDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell(), SWT.SAVE);
        	String fileName = dialog.open();    		
    		File f = new File(fileName);
    		f.createNewFile();
    	    FileWriter writer = new FileWriter(f); 
    	    writer.write("Rec Pos");
    	    for(String rec: GenerationGraph.getInstance().getOldRates().keySet()){
    	    	writer.write("\n"+rec + " " + GenerationGraph.getInstance().getOldRates().get(rec));	
    	    }    	     
    	    writer.flush();
    	    writer.close();    		
    	    MessageDialog.openInformation(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell(), "Export", "File exported");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
