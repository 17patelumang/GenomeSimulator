/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Generation parameter.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Generation extends Parameter {

    /**
     * @param  isOffset true if this generation is at a constant distance from another generation
     */
    boolean isOffset = false;
    /**
     * @param offsetGeneration offset generation
     */
    Generation offsetGeneration;
    /**
     * @param offset distance to offset generation
     */
    int offset;
    /**
     * @param G0 hold start
     * @param Ginf end
     */
    static Generation G0, Ginf;
    /**
     * @param created counter of created generations
     */
    static int created = 0;
    /**
     * nodes that are linked to this generation
     */
    HashSet<Node> nodes = new HashSet<Node>();
    
    public HashSet<Node> getNodes() {
		return nodes;
	}

	public void setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
	}

	/**
     * contraints (a set of nodes above and below, defined by edges)
     */
    
    HashSet<Generation> lessThan = new HashSet<Generation>();
    
    /**
     * contraints (a set of nodes above and below, defined by edges)
     */
    HashSet<Generation> moreThan = new HashSet<Generation>();
    /**
     * when an offset node is created, its constraints are added here
     */
    HashMap<Generation, Integer> lessThanOffsetNodes = new HashMap<Generation, Integer>();
    
    /**
     * when an offset node is created, its constraints are added here
     */
    HashMap<Generation, Integer> moreThanOffsetNodes = new HashMap<Generation, Integer>();

    /**
     * Generation constructor
     * @param value the generation value
     * @param gridPoints the number of grid points
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Generation(double value, int gridPoints) {
        super(value, gridPoints);
        created++;
    }

    /**
     * Return value
     * @return value of parameter
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public Double getValue() {
        if (isOffset == false) {
            return this.value;
        }
        else {
            return offsetGeneration.getValue() + offset;
        }
    }

    /**
     * Create a generation at a fixed distance from another generation
     * @param the other generation
     * @param the distance from it
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Generation(Generation offsetGeneration, int offset) throws Exception {
        if (offset < 1) {
            throw new Exception("Attemped to create a generation parameter with offset smaller than 1");
        }
        this.offsetGeneration = offsetGeneration;
        this.offset = offset;
        this.isOffset = true;
        this.isConstant = offsetGeneration.isConstant;
        created++;
    }

    /**
     * Adds upper bound
     * @param lessThan the bounding generation
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addLessThan(Generation lessThan) {
        // allow instantaneous edges
        if (lessThan != this) {
            this.lessThan.add(lessThan);
        }
    }

    /**
     * Adds lower bound
     * @param moreThan the bounding generation
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addMoreThan(Generation moreThan)
    {
        // allow instantaneous edges
        if (moreThan != this) 
        {
            this.moreThan.add(moreThan);
        }
    }

    /**
     * Returns min of upper bounds
     * @return min of upper bounds
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public double getAtMost() 
    {
        double min = Double.POSITIVE_INFINITY;
        for (Generation g : lessThan) 
        {
            if (g.getValue() < min) 
            {
                min = g.getValue();
            }
        }
        return min;
    }

    /**
     * Returns max of lower bounds
     * @return max of lower bounds
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public double getAtLeast() {
        double max = Double.NEGATIVE_INFINITY;
        for (Generation g : moreThan) {
            if (g.getValue() > max) {
                max = g.getValue();
            }
        }
        return max;
    }

    /**
     * Try to update a parameter
     * @return 0 if parameter updated, 1 if range violated, 2 if conservation violated, 3 if migration violated
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public int tryUpdate(double increment) {
        return 0;
    }
}
