package com.grapher.ui;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreateOldRateDialog extends TitleAreaDialog {

	  private Text txtRateId;
	  private String rateId; 

	  private Text txtStartSize;
	  private String startSize; 
	  
	  public CreateOldRateDialog(Shell parentShell) {
	    super(parentShell);
	  }

	  @Override
	  public void create() {
	    super.create();
	    setTitle("Create Old Rate");
	    setMessage("Enter Old Rate Information", IMessageProvider.INFORMATION);
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
	    lblRateName.setText("Rec");

	    GridData dataRateName = new GridData();
	    dataRateName.grabExcessHorizontalSpace = true;
	    dataRateName.horizontalAlignment = GridData.FILL;

	    txtRateId = new Text(container, SWT.BORDER);
	    txtRateId.setLayoutData(dataRateName);

//	    Start Size
	    Label lblName = new Label(container, SWT.NONE);
	    lblName.setText("Pos");

	    GridData fromSize = new GridData();
	    fromSize.grabExcessHorizontalSpace = true;
	    fromSize.horizontalAlignment = GridData.FILL;

	    txtStartSize = new Text(container, SWT.BORDER);
	    txtStartSize.setLayoutData(fromSize);
	    

	    GridData endSize = new GridData();
	    endSize.grabExcessHorizontalSpace = true;
	    endSize.horizontalAlignment = GridData.FILL;
	    
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
	    startSize = txtStartSize.getText();
	  }

	  @Override
	  protected void okPressed() {
	    saveInput();
	    super.okPressed();
	  }

	public String getStartSize() {
		return startSize;
	}

	public void setStartSize(String startSize) {
		this.startSize = startSize;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}	  
}
