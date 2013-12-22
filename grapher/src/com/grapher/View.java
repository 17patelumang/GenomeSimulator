package com.grapher;

import java.awt.Color;
import java.awt.TextArea;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.eclipse.jface.window.Window;
import org.eclipse.osgi.framework.internal.core.ConsoleManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import com.core.Edge;
import com.core.EdgePair;
import com.core.Generation;
import com.core.Migration;
import com.core.Node;
import com.core.Rate;
import com.core.RealFinalDemographicLanguage;
import com.core.Size;
import com.grapher.processor.FileProcessor;
import com.grapher.ui.GenerationGraph;
import com.grapher.ui.MyTitleAreaDialog;
import com.grapher.ui.NewEdgeDialog;
import com.grapher.ui.NewMigrationDialog;
import com.util.GenerationComparator;
import java.io.OutputStream;

import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

public class View extends ViewPart {
	
	public static final String ID = "grapher.view";
	private Graph graph;
	private Text text;

	private int layout = 1;

	FileProcessor fp = new FileProcessor();
	RealFinalDemographicLanguage rfdl;
	HashMap<String, GraphNode> myNodes = new HashMap<String, GraphNode>();
	HashMap<String, GraphConnection> myEdges = new HashMap<String, GraphConnection>();
	
	public View() {
		super();
	} 
	
	GraphNode currentNode;
		
	
	public RealFinalDemographicLanguage getRfdl() {
		return rfdl;
	}

	public void setRfdl(RealFinalDemographicLanguage rfdl) {
		this.rfdl = rfdl;
	}

	public HashMap<String, GraphNode> getMyNodes() {
		return myNodes;
	}

	public void setMyNodes(HashMap<String, GraphNode> myNodes) {
		this.myNodes = myNodes;
	}

	public HashMap<String, GraphConnection> getMyEdges() {
		return myEdges;
	}

	public void setMyEdges(HashMap<String, GraphConnection> myEdges) {
		this.myEdges = myEdges;
	}

	public void addNode(String nodeId, String genId, String genVal, String edgeId, String startSize, String endSize){	
		if(GenerationGraph.getInstance().getCurrentNode() != null){
			Generation g = rfdl.getGenerations().get(genId);
			if(g == null){
//				Constant grid points
				g = new Generation(Double.parseDouble(genVal), 2);
				g.setName(genId);
				rfdl.getGenerations().put(genId, g);
				rfdl.getAllVariables().put(genId, g);
			}
			Node n = new Node(g);
			n.setId(nodeId);
			rfdl.getNodes().put(nodeId, n);			
			rfdl.getAllVariables().put(nodeId, n);
			GraphNode gn = new GraphNode(graph, SWT.NONE, nodeId + "\n" + genId + "\n"+genVal, n);
			myNodes.put(nodeId, gn);
			
			Size startSz = new Size(Double.parseDouble(startSize), 1);
			Size endSz = new Size(Double.parseDouble(endSize), 1);
			
			Edge e = new Edge(n, (Node)GenerationGraph.getInstance().getCurrentNode().getData(), startSz, endSz);

			e.setId(edgeId);		
			rfdl.getEdges().put(edgeId, e);
			
			GraphConnection conn = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, gn, GenerationGraph.getInstance().getCurrentNode());
			conn.setText(e.getText());
			conn.setData(e);
			graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(), true);
//			
		}else{
			GenerationGraph.getInstance().setCurrentNode(null);
		}
	}
	public void addEdge(GraphNode node1, GraphNode node2, String edgeId, String startSize, String endSize){	
		if(GenerationGraph.getInstance().getCurrentNode() != null){			
			Size startSz = new Size(Double.parseDouble(startSize), 1);
			Size endSz = new Size(Double.parseDouble(endSize), 1);
			
		//	Edge e = new Edge((Node)GenerationGraph.getInstance().getCurrentNode().getData(), n, startSz, endSz);
			Edge e = new Edge((Node)node1.getData(),(Node)node2.getData(),endSz,startSz);

			e.setId(edgeId);		
			rfdl.getEdges().put(edgeId, e);			
			
			GraphConnection conn = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node1, node2);
			conn.setText(edgeId + "\n Start: " + startSize);
			conn.setData(e);
			myEdges.put(edgeId, conn);
			graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(), true);
		}else{
			GenerationGraph.getInstance().setCurrentNode(null);
		}
	}
	public void addRate(String migId, String startSize, String endSize){		
		Rate r = new Rate(Double.parseDouble(startSize), Integer.parseInt(endSize));
		r.setId(migId);
		r.setId(migId);
		rfdl.getRates().put(migId, r);		
	}
	public void addMigration(String rateId){
		GraphConnection gc0 = (GraphConnection)graph.getSelection().get(0);
		GraphConnection gc1 = (GraphConnection)graph.getSelection().get(1);
		Migration m = new Migration((Edge)gc0.getData(), (Edge)gc1.getData(), rfdl.getRates().get(rateId));
		rfdl.getMigrations().put(new EdgePair((Edge)gc0.getData(), (Edge)gc1.getData()), m);
	}
	
	public void newGraph(){
		myNodes.clear();		
		myEdges.clear();
		rfdl = null;
		try{
			InputStream is = getClass().getResourceAsStream("/d3.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));		
			rfdl = new RealFinalDemographicLanguage(br);
		}catch(Exception e){
			e.printStackTrace();
		}
		for(Object gn: graph.getNodes().toArray()){
			((GraphNode) gn).dispose();
		}
		try{
			rfdl.getGenerations().clear();
			rfdl.getAllVariables().clear();
			rfdl.getNodes().clear();
			rfdl.getEdges().clear();
			rfdl.getMigrations().clear();
			rfdl.getRates().clear();
		}catch(NullPointerException ne){
			rfdl.setGenerations(new HashMap<String, Generation>());
			rfdl.setGenerations(new HashMap<String, Generation>());
			rfdl.setNodes(new HashMap<String, Node>());
			rfdl.setEdges(new HashMap<String, Edge>());
			rfdl.setMigrations(new HashMap<EdgePair, Migration>());
			rfdl.setRates(new HashMap<String, Rate>());
		}
		
		Generation Ginf = new Generation(Double.POSITIVE_INFINITY, 0);
		Ginf.setName("Ginf");
		Ginf.setId("Ginf");
		rfdl.getGenerations().put("Ginf", Ginf);
		rfdl.getAllVariables().put("Ginf", Ginf);
		
		Node n = new Node(Ginf);
		n.setId("gnode");
		rfdl.getNodes().put("gnode", n);
		rfdl.getAllVariables().put("gnode", n);
				
		GraphNode gn = new GraphNode(graph, SWT.NONE, "Ginf", n);
		
		myNodes.put("Ginf", gn);
//		new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, GenerationGraph.getInstance().getCurrentNode(), gn);
		this.addListeners();
		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(), true);
	}
	
	public StringBuilder getString(){
		StringBuilder sb = new StringBuilder();
		for(String genId : rfdl.getGenerations().keySet()){
			if(!genId.toUpperCase().equals("GINF") && !genId.toUpperCase().equals("G0")){
				Generation gen = rfdl.getGenerations().get(genId);			  
				sb.append(gen.getId() + "=gen(" + gen.getValue().intValue() + "," + gen.getGridPoints() + ");\n");				
			}
		}
		sb.append("\n");
		for(String nodId: rfdl.getNodes().keySet()){
			Node n = rfdl.getNodes().get(nodId);	
			String genId = n.getGen().toString().split("_")[0];
		//	if(!genId.toUpperCase().equals("GI") && !genId.toUpperCase().equals("G0")){
				sb.append(n.getId() + "=node(" + genId + ");\n");	
		//	}						
		}
		
		sb.append("\n");
		
		for(String edgeId: rfdl.getEdges().keySet()){
			Edge e = rfdl.getEdges().get(edgeId);
			if(e.getN2().getGen().toString().split("_")[0].equals("Ginf")){
                sb.append(e.getId() + "=edge(" + e.getN1().getId() + "," + e.getN2().getId() + ",size(" + e.gets1().getValue().intValue() + "));\n");
            } 
            else  if (e.gets1().getValue().intValue() != e.gets2().getValue().intValue() )
            {
                sb.append(e.getId() + "=edge(" + e.getN1().getId() +"," + e.getN2().getId() + ",size(" + e.gets1().getValue().intValue() + "),size(" + e.gets2().getValue().intValue()  + "));\n");
            }else if (e.gets1().getValue().intValue() == e.gets2().getValue().intValue() )
            {
            	sb.append(e.getId() + "=edge(" + e.getN1().getId() +"," + e.getN2().getId() + ",size(" + e.gets1().getValue().intValue() + "));\n");
            }
		}
		for(String rateId: rfdl.getRates().keySet()){
			if(rateId.equals("R0") || rateId.equals("R1")){
				continue;
			}
			Rate r = rfdl.getRates().get(rateId);
			sb.append(r.getId() + "=rate(" + r.getValue() + "," + r.getGridPoints() + ");\n");
		}
		for(EdgePair migId: rfdl.getMigrations().keySet()){			
			Migration m = rfdl.getMigrations().get(migId);
			sb.append("migration(" + m.getFromEdge().getId() + "," + m.getToEdge().getId() + "," + m.getR().getId() + ");\n");
		}
		return sb;
	}

	
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	
	public void addListeners(){
		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(), true);
		graph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				if(graph.getSelection().size() == 2){
					if(graph.getSelection().get(0) instanceof GraphNode &&
							graph.getSelection().get(1) instanceof GraphNode){
						try{
							NewEdgeDialog dialog = new NewEdgeDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell());
					    	dialog.create();
					    	if (dialog.open() == Window.OK) {			    		
						      	View v = GenerationGraph.getInstance().getMainView();
					    		v.addEdge((GraphNode)graph.getSelection().get(0), (GraphNode)graph.getSelection().get(1), dialog.getEdgeId(), dialog.getEndSize(), dialog.getStartSize());
					    	}
				    	}catch(Exception ex){
				    		ex.printStackTrace();
				    	}						
					}else{
						if(graph.getSelection().get(0) instanceof GraphConnection &&
								graph.getSelection().get(1) instanceof GraphConnection){
							try{
								NewMigrationDialog dialog = new NewMigrationDialog(GenerationGraph.getInstance().getMainView().getSite().getWorkbenchWindow().getShell());
						    	dialog.create();
						    	if (dialog.open() == Window.OK) {			    		
							      	View v = GenerationGraph.getInstance().getMainView();
							      	v.addMigration(dialog.getRateId());
						    	}
					    	}catch(Exception ex){
					    		ex.printStackTrace();
					    	}						
						}
					}
				}else if(e.item instanceof GraphNode){
					GenerationGraph.getInstance().setCurrentNode((GraphNode)e.item);
					for(String cId: myEdges.keySet()){						
						myEdges.get(cId).setText(rfdl.getEdges().get(cId).getText());
						myEdges.get(cId).unhighlight();	
					}
				}else if(e.item instanceof GraphConnection){
					GraphConnection edge =  (GraphConnection)e.item;					
					Edge edg = (Edge) edge.getData();				
					for(String cId: myEdges.keySet()){						
						myEdges.get(cId).setText(rfdl.getEdges().get(cId).getText());
						if(!cId.equals(edg.getId())){
							myEdges.get(cId).unhighlight();	
						}
					}
					
					for(EdgePair ePair: rfdl.getMigrations().keySet()){						
						Migration m = rfdl.getMigrations().get(ePair);
						if(m.getFromEdge().getId().equals(edg.getId()) || m.getToEdge().getId().equals(edg.getId())){
							GraphConnection fromE = myEdges.get(m.getToEdge().getId());
							GraphConnection toE = myEdges.get(m.getFromEdge().getId());							
							fromE.highlight();			
							if(fromE.getText().indexOf(m.getR().getId()) == -1){
								fromE.setText(fromE.getText() + "\n" + m.getR().getId());
							}
							toE.highlight();
							if(toE.getText().indexOf(m.getR().getId()) == -1){
								toE.setText(toE.getText() + "\n" + m.getR().getId());	
							}
														
						}
					}	
//					graph.redraw();
				}
			}
		});
	}
	public void loadGraph(String fileName){
		myNodes.clear();		
		myEdges.clear();
		// remove all the connections
		Object[] objects = graph.getConnections().toArray() ;
		for ( int x = 0 ; x < objects.length ; x++ ) {
			((GraphConnection) objects[x]).dispose() ;
		}
		// remove all the nodes
		objects = graph.getNodes().toArray() ;
		for ( int x = 0 ; x < objects.length ; x++ ) {
			((GraphNode) objects[x]).dispose();
		}	
		rfdl = null;
		rfdl = fp.process(fileName);
		
		for(String nodeId: rfdl.getNodes().keySet()){
			Node n = rfdl.getNodes().get(nodeId);			
			String s = nodeId + 
					" \n " + 
					n.getGen().toString().substring(0,n.getGen().toString().indexOf("_")).toUpperCase() +
					" \n "; 
				if(!n.getGen().toString().substring(0,n.getGen().toString().indexOf("_")).toUpperCase().equals("GINF")){
				s=s+	n.getGen().getValue().intValue()  ;
				}
				
			GraphNode gn = new GraphNode(graph, SWT.NONE, s, n);			
			gn.setBackgroundColor(graph.getDisplay().getSystemColor(SWT.COLOR_GRAY));
			myNodes.put(nodeId, gn);
		}		
		for (String edgeId :rfdl.getEdges().keySet()){
			Edge e = rfdl.getEdges().get(edgeId);
			GraphConnection cn = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, myNodes.get(e.getN1().getId()), myNodes.get(e.getN2().getId())
					); 
			cn.setText(e.getText());
			cn.setData(e);
			myEdges.put(e.getId(), cn);
		}		
		this.addListeners();

	}
//	Important - Function - First to fire
	public void createPartControl(Composite parent) {
				
		parent.setLayout(new FillLayout());
		Composite top = new Composite(parent, SWT.BORDER);
		top.setLayout(new RowLayout(SWT.VERTICAL));
		
		GenerationGraph.getInstance().setMainView(this);
		parent.setSize(1000, 800);
		graph = new Graph(top, SWT.NONE);		
		text = new Text(top, SWT.NONE | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		  OutputStream out = new OutputStream() {
		   @Override
		   public void write(int b) throws IOException {
		    if (text.isDisposed()) return;
		    text.append(String.valueOf((char) b));
		   }
		  };
		  final PrintStream oldOut = System.out;
		  System.setOut(new PrintStream(out));
		  text.addDisposeListener(new DisposeListener() {
		   public void widgetDisposed(DisposeEvent e) {
		    System.setOut(oldOut);
		   }
		  });	
		
		graph.setLayoutData(new RowData(1000, 600));
		text.setLayoutData(new RowData(985, 150));
		
		  RowLayout rowLayout = new RowLayout();		  
		  rowLayout.wrap = false;
		  rowLayout.pack = false;
		  rowLayout.justify = true;
		  rowLayout.type = SWT.VERTICAL;
		  rowLayout.marginLeft = 5;
		  rowLayout.marginTop = 5;
		  rowLayout.marginRight = 5;
		  rowLayout.marginBottom = 5;
		  rowLayout.spacing = 0;
		  parent.setLayout(rowLayout);		  
		  parent.pack();
	}



	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}


}