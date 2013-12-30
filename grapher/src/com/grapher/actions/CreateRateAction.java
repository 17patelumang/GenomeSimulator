package com.grapher.actions;


/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;

import com.grapher.View;
import com.grapher.ui.GenerationGraph;
import com.grapher.ui.MyTitleAreaDialog;
import com.grapher.ui.NewRateDialog;

public class CreateRateAction extends Action {
	
    public CreateRateAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.newrate");
    }
    
    public void run() {
//    	Nasty code .. will change when I understand eclipse plugins
    	NewRateDialog dialog = new NewRateDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell());
    	dialog.create();
    	if (dialog.open() == Window.OK) {
	      	View v = GenerationGraph.getInstance().getMainView();
	      	v.addRate(dialog.getRateId(), dialog.getStartSize(), dialog.getEndSize());
    	} 
    }
}
