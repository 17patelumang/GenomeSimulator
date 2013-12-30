package com.grapher.ui;


/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */

import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.osgi.service.log.LogService;

import com.grapher.View;

public class GenerationGraph extends Object {

	private static GenerationGraph me;
	private static HashMap<String, Integer> oldRates = new HashMap<String, Integer>();
	private LogService log;
	
	public LogService getLog() {
		return this.log;
	}

	public void setLog(LogService log) {
		this.log = log;
	}

	View mainView = null;
	GraphNode currentNode = null;
	
	
	
	public static HashMap<String, Integer> getOldRates() {
		return oldRates;
	}

	public static void setOldRates(HashMap<String, Integer> oldRates) {
		GenerationGraph.oldRates = oldRates;
	}

	public View getMainView() {
		return mainView;
	}

	public void setMainView(View mainView) {
		this.mainView = mainView;
	}

	
	public GraphNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(GraphNode currentNode) {
		this.currentNode = currentNode;
	}

	private GenerationGraph() {
		super();		
	}
	
	public static synchronized GenerationGraph getInstance(){
		if(me == null){
			me = new GenerationGraph();
		}
		return me;
	}

}
