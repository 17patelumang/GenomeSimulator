/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Represents a population in the population graph.
 * Edge has start/end time and start/end size.
 * If the size is the same variable, the population has constant size.
 * Exponential growth/decrease is assumed otherwise.
 * If start/end time are the same, this is an instantaneous edge (has time-length epsilon).
 * Migrations that involve this kind of edges are processed first.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Edge implements Comparable<Edge> {

   /** @param id name of the edge */
    String id;
    /** @param n1,n2 start/end node which contains time  */
    Node n1, n2;
    /** @param s1,s2 start/end size */
    Size s1, s2;
    /** @param migOut migration out */
    private TreeMap<Edge, Migration> migOut = new TreeMap<Edge, Migration>();
    /** @param migIn migration in */
    private TreeMap<Edge, Migration> migIn = new TreeMap<Edge, Migration>();
    /** @param created counter of created edges object */
    static int created = 0;

    
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getText(){
		return this.getId() + "\n Start: " + this.gets1().getValue() + "\n End: " + this.gets2().getValue();
	}
	public Node getN1() {
		return n1;
	}

	public Size gets1() {
		return s1;
	}
	
	public Size gets2() {
		return s2;
	}
	
	public void setN1(Node n1) {
		this.n1 = n1;
	}

	public Node getN2() {
		return n2;
	}

	public void setN2(Node n2) {
		this.n2 = n2;
	}

	/**
     * Builds an edge
     * @param n1 Start node
     * @param n2 End node
     * @param s1 Start size
     * @param s2 End size
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Edge(Node n1, Node n2, Size s1, Size s2) {
        this.n1 = n1;
        this.n2 = n2;
        this.s1 = s1;
        this.s2 = s2;
        n1.gen.addLessThan(n2.gen); // ?
        n2.gen.addMoreThan(n1.gen); // ?
        created++;
    }

    /**
     * Sets edge name
     * @param id The name
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void setName(String id) {
        this.id = id;
    }

    /**
     * Return population size at specific time.
     * @param time the required time
     * @return size at required time
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public double getSizeAt(double time) throws Exception {
        if (time < n1.gen.value || time > n2.gen.value) {
            throw new Exception(time + " is not within edge range.");
        }
        if (s1 == s2) {
            return s1.value;
        } else {
            double G = n2.gen.value - n1.gen.value;
            double C = s1.value;
            double A = s2.value;
            return C * Math.pow((A / C), time / G);
        }
    }

    /**
     * Adds an incoming migration
     * @param m The migration
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addMigIn(Edge e, Migration m) {
        migIn.put(e, m); //?
//        System.out.println("Added. " + this.migIn.size());
    }

    /**
     * get method for the set of incoming migrations
     * @return the set of incoming migrations
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public TreeMap<Edge, Migration> getMigIn()
    {
        return migIn;
    }

    /**
     * Adds an outgoing migration
     * @param m The migration
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addMigOut(Edge e, Migration m) {
        migOut.put(e, m);
//        System.out.println("Added. " + this.migOut.size());
    }

    /**
     * get method for the set of outgoing migrations
     * @return the set of outgoing migrations
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public TreeMap<Edge, Migration> getMigOut() {
        return migOut;
    }

    /**
     * Prints edge details
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public String toString() {
        return this.id + ":" + n1.toString() + "->" + n2.toString();
    }

    /**
     * makes string of names of all edges being passed
     * @param edgeSet set of edges
     * @return
     */
    public static String edgeSetToString(TreeSet<Edge> edgeSet) 
    {
        if (edgeSet.size() == 0) 
        {
            return "";
        }
        Iterator edgeIt = edgeSet.iterator();
        Edge e = (Edge) edgeIt.next();
        String s = e.id;
        while (edgeIt.hasNext()) 
        {
            e = (Edge) edgeIt.next();
            s += "-" + e.id;
        }
        return s;
    }

    /**
     * returns true if the edge is instantaneous
     * @return true if the edge is instantaneous
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public boolean isInstantaneous() {
        return this.n1.gen == this.n2.gen;
    }

    /**
     * Implements comparator
     * @return +1 if e2 smaller, -1 if greater, 0 if same id
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public int compareTo(Edge e2) {
        if (this.n2.gen.getValue() > e2.n2.gen.getValue()) {
            return 1;
        } else if (this.n2.gen.getValue() < e2.n2.gen.getValue()) {
            return -1;
        } else {
            return this.id.compareTo(e2.id);
        }
    }
}

