package com.grapher.actions;


/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */


import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.grapher.ui.GenerationGraph;

public class OpenAction extends Action {
	
    public OpenAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.open");
    }
    
    public void run() {
    	FileDialog dialog =  new FileDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell(), SWT.OPEN);
    	String fileName = dialog.open();
    	GenerationGraph.getInstance().getMainView().loadGraph(fileName);
    }
}
