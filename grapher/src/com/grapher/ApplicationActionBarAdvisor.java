package com.grapher;

import java.rmi.server.ExportException;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.grapher.actions.CreateOldRateAction;
import com.grapher.actions.CreateRateAction;
import com.grapher.actions.CreateNodeAction;
import com.grapher.actions.ExportAction;
import com.grapher.actions.ExportOldFormatAction;
import com.grapher.actions.ExportOldRateAction;
import com.grapher.actions.NewGraphAction;
import com.grapher.actions.OpenAction;
import com.grapher.ui.NewRateDialog;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */

/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
	
	private CreateNodeAction newNodeAction;
	private ExportAction exportAction;
	private ExportOldFormatAction exportOldFormatAction;
	private NewGraphAction newGraphAction;
	private OpenAction openAction;
	private CreateRateAction newRateAction;
	private CreateOldRateAction oldRateAction;
	private ExportOldRateAction exportOldRate;
	

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		super.makeActions(window);
		newNodeAction = new CreateNodeAction(window, "&New Node");
		newNodeAction.setAccelerator(SWT.CTRL + 'N');
		
		newGraphAction = new NewGraphAction(window, "New &Graph");
		newGraphAction.setAccelerator(SWT.CTRL + 'G');
		
		openAction = new OpenAction(window, "&Open");
		openAction.setAccelerator(SWT.CTRL + 'O');
		
		exportAction = new ExportAction(window, "&Export");
		exportAction.setAccelerator(SWT.CTRL + 'E');
		
		exportOldFormatAction = new ExportOldFormatAction(window, "Export Old &Format");
		exportOldFormatAction.setAccelerator(SWT.CTRL + 'F');
		
		newRateAction = new CreateRateAction(window, "New &Rate");
		newRateAction.setAccelerator(SWT.CTRL + 'R');
		
		exportOldRate = new ExportOldRateAction(window, "Export Old R&ate");
		exportOldRate.setAccelerator(SWT.CTRL + 'A');
		
		oldRateAction = new CreateOldRateAction(window, "&Create Old Rate");
		oldRateAction.setAccelerator(SWT.CTRL + 'C');
		
        register(newNodeAction);
        register(exportAction);
        register(exportOldFormatAction);
	}
	
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		// TODO Auto-generated method stub
		super.fillMenuBar(menuBar);
		MenuManager fileMenu = new MenuManager("File", IWorkbenchActionConstants.M_FILE);
		MenuManager oldSimulatorMenu = new MenuManager("Old Simulator", IWorkbenchActionConstants.M_EDIT);
		menuBar.add(fileMenu);
		menuBar.add(oldSimulatorMenu);
		fileMenu.add(newNodeAction);
		fileMenu.add(newRateAction);
		fileMenu.add(oldRateAction);
		fileMenu.add(newGraphAction);
		fileMenu.add(openAction);
		fileMenu.add(exportAction);
		
		oldSimulatorMenu.add(exportOldFormatAction);
		oldSimulatorMenu.add(exportOldRate);
		
		
	}
}
