/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

/**
 * Abstract class for a generic parameter.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public abstract class Parameter implements Comparable<Parameter> {

    /**
     * @param epsilon for numerical precision, when equalities are required, use at most epsilon difference.
     */
    static final double epsilon = 1E-10;
    /**
     * @param value the value of the parameter
     */
    protected Double value;
    /**
     * @param gridPoints the number of grid points in the initialization
     */
    int gridPoints;
    /**
     * @param isConstant true if grid points = 0
     */
    boolean isConstant;
    /**
     * @param id name of the parameter
     */
    String id;

    /**
     * Prints parameter
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public String toString() {
        return this.id + "_" + this.value + "_" + this.gridPoints;
    }

    /**
     * Return value
     * @return value of parameter
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Double getValue() {
        return this.value;
    }

    /**
     * Empty constructor, builds empty parameter
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Parameter() {
    }

    /**
     * Builds a parameter
     * @param value The parameter value
     * @param gridPoints Number of grid points
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Parameter(double value, int gridPoints) {
        this.value = value;
        this.gridPoints = gridPoints;
        this.isConstant = (this.gridPoints == 0) ? true : false;
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
     * comparison is on value, if same, use name if specified, otherwise return any.
     * NOTE: if no name and same value, sets may keep one of two identical objects
     * @param p2 The other parameter
     * @return 1 if the value of p2 is smaller, -1 if larger. If value is same compare names. 0 if same value and no name set.
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public int compareTo(Parameter p2) {
        if (p2.getValue() < this.getValue()) {
            return 1;
        } else if (p2.getValue() > this.getValue()) {
            return -1;
        } else {
            if (id != null && p2.id != null) {
                return id.compareTo(p2.id);
            } else {
                // name not set yet, return same
                return 0;
            }
        }
    }

    /**
     * Try to update a parameter
     * @return 0 if parameter updated, 1 if range violated, 2 if conservation violated, 3 if migration violated
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public abstract int tryUpdate(double increment);

	public int getGridPoints() {
		return gridPoints;
	}

	public void setGridPoints(int gridPoints) {
		this.gridPoints = gridPoints;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    
}
