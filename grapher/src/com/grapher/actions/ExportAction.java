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

public class ExportAction extends Action {
	
    public ExportAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.export");
    }
    
    public void run() {
//    	Nasty code .. will change when I understand eclipse plugins
    	View v = GenerationGraph.getInstance().getMainView();
    	try{
        	FileDialog dialog =  new FileDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell(), SWT.SAVE);
        	String fileName = dialog.open();    		
    		File f = new File(fileName);
    		f.createNewFile();
    	    FileWriter writer = new FileWriter(f); 
    	    writer.write(v.getString().toString()); 
    	    writer.flush();
    	    writer.close();    		
    	    MessageDialog.openInformation(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell(), "Export", "File exported");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
