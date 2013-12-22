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

public class MyTitleAreaDialog extends TitleAreaDialog {

	  private Text txtNodeId;
	  private String nodeId; 

	  private Text txtGenId;
	  private String genId; 
	  
	  private Text txtGenVal;
	  private String genVal; 	  

	  private Text txtEdgeId;
	  private String edgeId; 

	  private Text txtStartSize;
	  private String startSize; 
	  
	  private Text txtEndSize;
	  private String endSize; 

	  public MyTitleAreaDialog(Shell parentShell) {
	    super(parentShell);
	  }

	  @Override
	  public void create() {
	    super.create();
	    setTitle("Create New Node");
	    setMessage("Enter Node Name", IMessageProvider.INFORMATION);
	  }

	  @Override
	  protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout layout = new GridLayout(2, false);
	    
	    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    container.setLayout(layout);

//	    Node Name
	    Label lbtFirstName = new Label(container, SWT.NONE);
	    lbtFirstName.setText("Node Name");

	    GridData dataFirstName = new GridData();
	    dataFirstName.grabExcessHorizontalSpace = true;
	    dataFirstName.horizontalAlignment = GridData.FILL;

	    txtNodeId = new Text(container, SWT.BORDER);
	    txtNodeId.setLayoutData(dataFirstName);

//	    Generation Name
	    Label lblGenName = new Label(container, SWT.NONE);
	    lblGenName.setText("Generation Name");

	    GridData dataGenName = new GridData();
	    dataGenName.grabExcessHorizontalSpace = true;
	    dataGenName.horizontalAlignment = GridData.FILL;

	    txtGenId = new Text(container, SWT.BORDER);
	    txtGenId.setLayoutData(dataGenName);

//	    Generation Name
	    Label lblGenVal = new Label(container, SWT.NONE);
	    lblGenVal.setText("Generation Value");

	    GridData dataGenVal = new GridData();
	    dataGenVal.grabExcessHorizontalSpace = true;
	    dataGenVal.horizontalAlignment = GridData.FILL;

	    txtGenVal = new Text(container, SWT.BORDER);
	    txtGenVal.setLayoutData(dataGenVal);

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
	    nodeId = txtNodeId.getText();
	    genId = txtGenId.getText();
	    genVal = txtGenVal.getText();
	    edgeId = txtEdgeId.getText();	    
	    startSize = txtStartSize.getText();
	    endSize = txtEndSize.getText();
	  }

	  @Override
	  protected void okPressed() {
	    saveInput();
	    super.okPressed();
	  }

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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

	public String getGenId() {
		return genId;
	}

	public void setGenId(String genId) {
		this.genId = genId;
	}

	public String getEdgeId() {
		return edgeId;
	}

	public void setEdgeId(String edgeId) {
		this.edgeId = edgeId;
	}

	public String getGenVal() {
		return genVal;
	}

	public void setGenVal(String genVal) {
		this.genVal = genVal;
	}
	
	
	  
}
