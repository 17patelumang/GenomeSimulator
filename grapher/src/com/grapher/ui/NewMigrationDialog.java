package com.grapher.ui;

/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */


import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.grapher.View;

public class NewMigrationDialog extends TitleAreaDialog {

	  private Combo txtRateId;
	  private String rateId; 

	  public NewMigrationDialog(Shell parentShell) {
	    super(parentShell);
	  }

	  @Override
	  public void create() {
	    super.create();
	    setTitle("Pick a Rate");
	    setMessage("Pick Rate", IMessageProvider.INFORMATION);
	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout layout = new GridLayout(2, false);
	    
	    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    container.setLayout(layout);

//	    Rate Name
	    Label lblRateName = new Label(container, SWT.NONE);
	    lblRateName.setText("Rate Name");

	    GridData dataRateName = new GridData();
	    dataRateName.grabExcessHorizontalSpace = true;
	    dataRateName.horizontalAlignment = GridData.FILL;

	    txtRateId = new Combo(container, SWT.BORDER);
	    View v = GenerationGraph.getInstance().getMainView();
	    for(String key: v.getRfdl().getRates().keySet()){
	    	if(key.equalsIgnoreCase("R0") || key.equalsIgnoreCase("R1")) continue;
	    	txtRateId.add(key);
	    }
	    txtRateId.setLayoutData(dataRateName);	    
	    
	    return area;
	    
	  }	  


	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

	  // save content of the Text fields because they get disposed
	  // as soon as the Dialog closes
	  private void saveInput() {
	    rateId = txtRateId.getText();	    
	  }

	  @Override
	  protected void okPressed() {
	    saveInput();
	    super.okPressed();
	  }

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}	  
}
