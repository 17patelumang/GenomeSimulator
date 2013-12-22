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

public class NewEdgeDialog extends TitleAreaDialog {

	  private Text txtEdgeId;
	  private String edgeId; 

	  private Text txtStartSize;
	  private String startSize; 
	  
	  private Text txtEndSize;
	  private String endSize; 

	  public NewEdgeDialog(Shell parentShell) {
	    super(parentShell);
	  }

	  @Override
	  public void create() {
	    super.create();
	    setTitle("Create New Edge");
	    setMessage("Enter Edge Information", IMessageProvider.INFORMATION);
	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout layout = new GridLayout(2, false);
	    
	    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    container.setLayout(layout);

//	    Edge Name
	    Label lblEdgeName = new Label(container, SWT.NONE);
	    lblEdgeName.setText("Edge Name");

	    GridData dataEdgeName = new GridData();
	    dataEdgeName.grabExcessHorizontalSpace = true;
	    dataEdgeName.horizontalAlignment = GridData.FILL;

	    txtEdgeId = new Text(container, SWT.BORDER);
	    txtEdgeId.setLayoutData(dataEdgeName);

//	    Start Size
	    Label lblName = new Label(container, SWT.NONE);
	    lblName.setText("Start Size");

	    GridData fromSize = new GridData();
	    fromSize.grabExcessHorizontalSpace = true;
	    fromSize.horizontalAlignment = GridData.FILL;

	    txtStartSize = new Text(container, SWT.BORDER);
	    txtStartSize.setLayoutData(fromSize);
	    
//	    End Size	    
	    Label lblEndSize = new Label(container, SWT.NONE);
	    lblEndSize.setText("End Size");

	    GridData endSize = new GridData();
	    endSize.grabExcessHorizontalSpace = true;
	    endSize.horizontalAlignment = GridData.FILL;

	    txtEndSize = new Text(container, SWT.BORDER);
	    txtEndSize.setLayoutData(fromSize);
	    
	    
	    return area;
	    
	  }	  


	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

	  // save content of the Text fields because they get disposed
	  // as soon as the Dialog closes
	  private void saveInput() {
	    edgeId = txtEdgeId.getText();	    
	    startSize = txtStartSize.getText();
	    endSize = txtEndSize.getText();
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

	public String getEndSize() {
		return endSize;
	}

	public void setEndSize(String endSize) {
		this.endSize = endSize;
	}

	public String getEdgeId() {
		return edgeId;
	}

	public void setEdgeId(String edgeId) {
		this.edgeId = edgeId;
	}	  
}
