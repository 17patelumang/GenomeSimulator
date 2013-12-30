/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

/**
 * Migration between two populations.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Migration {


	/**
	 * id name of migration event
	 */
    String id;
    /**
     * fromEdge,toEdge the two edges (populations)
     */
     
    Edge fromEdge, toEdge;
    /**
     * r the rate parameter
     */
    Rate r;
    /**
     * counter of created objects
     */
    
    static int created = 0;

    /**
     * Constructor
     * @param e1 the first population
     * @param e2 the second population
     * @param r the migration rate
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Migration(Edge e1, Edge e2, Rate r) {
        this.fromEdge = e1;
        this.toEdge = e2;
        this.r = r;
        if (e1 != e2) {
            e1.addMigOut(e2, this);
            e2.addMigIn(e1, this);
        }
        created++;
    }

    /**
     * Set the parameter name
     * @param id The name
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void setName(String id) {
        this.id = id;
    }

    /**
     * toString
     * @return string for object
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public String toString() {
        return this.id + ":" + this.fromEdge.id + "->" + this.toEdge.id + "=" + this.r.toString();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Edge getFromEdge() {
		return fromEdge;
	}

	public void setFromEdge(Edge fromEdge) {
		this.fromEdge = fromEdge;
	}

	public Edge getToEdge() {
		return toEdge;
	}

	public void setToEdge(Edge toEdge) {
		this.toEdge = toEdge;
	}

	public Rate getR() {
		return r;
	}

	public void setR(Rate r) {
		this.r = r;
	}
    
    

}
