/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * In the population graph a node is a junction across edges where events such as
 * merge, split, change in growth rate and migration may occur.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Node implements Comparable<Node> {

    /**
     * @param id name of node
     */
    String id;
    /**
     * @param gen time of node 
     */
    Generation gen;
    /**
     * @param sizes all sizes in node
     */
    TreeSet<Size> sizes = new TreeSet<Size>();
    /**
     * @param pivot have precedence in balancing added/removed mass if edge in/out size is conserved
     */
    TreeSet<Size> pivot = new TreeSet<Size>();
    /**
     * @param inEdges incoming edges
     */
    HashSet<Edge> inEdges = new HashSet<Edge>();
    /**
     * @param outEdges outgoing edges
     */
    HashSet<Edge> outEdges = new HashSet<Edge>();
    /**
     * @param conservation  if true, sum of incoming population sizes must be same as outgoing
     */
    boolean conservation = false;
    /**
     * @param created counter of created objects
     */
    static int created = 0;

    /**
     * Builds a node
     * @param gen generation of node
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Node(Generation gen) {
        this.gen = gen;
        this.gen.nodes.add(this);
        created++;
    }
    
    

    public Generation getGen() {
		return gen;
	}



	public void setGen(Generation gen) {
		this.gen = gen;
	}



	public HashSet<Edge> getInEdges() {
		return inEdges;
	}



	public void setInEdges(HashSet<Edge> inEdges) {
		this.inEdges = inEdges;
	}



	public HashSet<Edge> getOutEdges() {
		return outEdges;
	}



	public void setOutEdges(HashSet<Edge> outEdges) {
		this.outEdges = outEdges;
	}



	/**
     * Sets node name
     * @param id The name
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void setName(String id) {
        this.id = id;
    }

    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	/**
     * Adds a size parameter to node size list
     * @param s The size
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addSize(Size s) {
        sizes.add(s);
    }

    /**
     * Adds an incoming edge
     * @param e The edge
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addInEdge(Edge e) {
        inEdges.add(e);
    }

    /**
     * Adds an outgoing edge
     * @param e The edge
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addOutEdge(Edge e) {
        outEdges.add(e);
    }

    /**
     * Adds a pivot to the node
     * @param p The size pivot
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addPivot(Size s) throws Exception {
        if (!sizes.contains(s)) {
            throw new Exception("Trying to add pivot " + s.id + ", but it is not part of node " + this.id);
        }
        if (s.isConstant) {
            throw new Exception("Trying to add a constant size as pivot " + s.id);
        }
        sizes.remove(s);
        pivot.add(s);
    }

    /**
     * returns sum of incoming sizes
     * @return the sum of incoming sizes
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public double getSumOfIncoming() {
        double in = 0.0;
        for (Edge e : inEdges) {
            in += e.s2.value;
        }
        return in;
    }

    /**
     * returns sum of outGoing sizes
     * @return the sum of outGoing sizes
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public double getSumOfOutGoing() {
        double out = 0.0;
        for (Edge e : outEdges) {

            out += e.s1.value;
        }
        return out;
    }

    /**
     * checks if an outgoing edge is instantaneous
     * @return true if one outgoing edge is instantaneous
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public boolean hasInstantaneousEdgesInOutput() {
        for (Edge e : this.outEdges) {
            if (e.n1.gen == e.n2.gen) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints node details
     * @return the string version
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public String toString() {
        return id + "(" + gen.value + ")(" + inEdges.size() + "_" + outEdges.size() + ")";
    }

    /**
     * Implements comparator
     * @return comparison of id strings
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public int compareTo(Node n2) {
        return this.id.compareTo(n2.id);
    }
}
