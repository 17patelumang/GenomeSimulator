package com.grapher.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;

import com.grapher.View;
import com.grapher.ui.CreateOldRateDialog;
import com.grapher.ui.GenerationGraph;
import com.grapher.ui.MyTitleAreaDialog;
import com.grapher.ui.NewRateDialog;

public class CreateOldRateAction extends Action {
	
    public CreateOldRateAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.createoldrate");
    }
    
    public void run() {
//    	Nasty code .. will change when I understand eclipse plugins
    	
    	CreateOldRateDialog dialog = new CreateOldRateDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell());
    	dialog.create();
    	if (dialog.open() == Window.OK) {
    		GenerationGraph.getInstance().getOldRates().put(dialog.getRateId(), Integer.valueOf(dialog.getStartSize()));
    	} 
    }
}
