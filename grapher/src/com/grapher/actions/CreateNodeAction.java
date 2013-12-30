package com.grapher.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;

import com.grapher.View;
import com.grapher.ui.GenerationGraph;
import com.grapher.ui.MyTitleAreaDialog;

public class CreateNodeAction extends Action {
	
    public CreateNodeAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.newnode");
    }
    
    public void run() {
    	MyTitleAreaDialog dialog = new MyTitleAreaDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell());
    	dialog.create();
    	if (dialog.open() == Window.OK) {
	      	View v = GenerationGraph.getInstance().getMainView();
	      	v.addNode(dialog.getNodeId(), dialog.getGenId(), dialog.getGenVal(), dialog.getEdgeId(), dialog.getStartSize(), dialog.getEndSize());
    	} 
    }
}
