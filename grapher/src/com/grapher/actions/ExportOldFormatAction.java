package com.grapher.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.core.Edge;
import com.core.Generation;
import com.core.Node;
import com.core.RealFinalDemographicLanguage;
import com.grapher.View;
import com.grapher.ui.GenerationGraph;
import com.grapher.ui.MyTitleAreaDialog;
import com.util.GenerationComparator;

public class ExportOldFormatAction extends Action {
	
    public ExportOldFormatAction(IWorkbenchWindow window, String label) {
            setText(label);
            setId("grapher.exportoldformat");
    }
    public StringBuilder getOldString(RealFinalDemographicLanguage rfdl){
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		ArrayList<Generation> gens = new ArrayList<Generation>();
//		ArrayList<String> nodeKeys = new ArrayList<String>();
		
		for(String genId : rfdl.getGenerations().keySet()){
			gens.add(rfdl.getGenerations().get(genId));
		}
//		for(String nodeId : rfdl.getNodes().keySet()){
//			nodeKeys.add(nodeId);
//		}
		
		Collections.sort(gens, new GenerationComparator());
		
		ArrayList<String> nodeKeys1 = new ArrayList<String>();
		ArrayList<String> nodeKeys2 = new ArrayList<String>();
		
		int gens_size=gens.size();
		
		int gens_count=0;
		for(Generation gen : gens){			
			gens_count=gens_count+1;
			
			if(!gen.getId().toUpperCase().equals("GINF")){		
				
				sb.append(gen.getValue().intValue());
				
				if(gen.getId().equalsIgnoreCase("G0")){
					for(String edgeId: rfdl.getEdges().keySet()){
						Edge e = rfdl.getEdges().get(edgeId);
						if(e.getN1().getGen().getId().equals(gen.getId())){
							sb.append(" " + e.gets1().getValue().intValue());
							nodeKeys1.add(e.getN1().getId());
							sb1.append((nodeKeys1.indexOf(e.getN1().getId()) + 1) + "-");							
							if(nodeKeys2.indexOf(e.getN2().getId()) == -1){
								nodeKeys2.add(e.getN2().getId());
							}
							sb1.append((nodeKeys2.indexOf(e.getN2().getId()) + 1) + " ");
						}
					}					
				}else{
					nodeKeys1.clear();
					nodeKeys1.addAll(nodeKeys2);
					nodeKeys2.clear();					
					
					for(String nodeId: nodeKeys1){
						Node n = rfdl.getNodes().get(nodeId);						
						sb.append(" " + (int)n.getSumOfIncoming());
						
						for(Edge e: n.getOutEdges()){
							if(!e.getN2().getGen().getId().equalsIgnoreCase("GINF") &&
								!e.getN1().getGen().getId().equalsIgnoreCase(e.getN2().getGen().getId())){
								
								sb1.append((nodeKeys1.indexOf(e.getN1().getId()) + 1) + "-");							
								if(nodeKeys2.indexOf(e.getN2().getId()) == -1){
									nodeKeys2.add(e.getN2().getId());
								}
								sb1.append((nodeKeys2.indexOf(e.getN2().getId()) + 1) + " ");								
							}
						}
					}
				}
				
				if(gens_count<gens_size-1){
				sb.append("\n");
				sb.append(sb1);
				}
			}
			
			if(gens_count<gens_size-1){
				
			sb.append("\n");
			sb1 = null;
			sb1 = new StringBuilder();	
			}
		}
		return sb;
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
    		InputStream is = new ByteArrayInputStream(v.getString().toString().getBytes());
    		BufferedReader br = new BufferedReader(new InputStreamReader(is));    		
    		RealFinalDemographicLanguage model = new RealFinalDemographicLanguage(br);    		
    		model.narrate();    		    	    
    	    writer.write(this.getOldString(model).toString()); 
    	    writer.flush();
    	    writer.close();    		
    	    MessageDialog.openInformation(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell(), "Export Old Format", "File exported");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
