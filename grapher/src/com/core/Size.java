/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.core;

/**
 * Size parameter.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Size extends Parameter {

    /**
     * @param min lower bound, upper bound
     */
    Double min = 0.0, max = Double.POSITIVE_INFINITY;
    /**
     * @param counter of created objects
     */
    static int created = 0;

    /**
     * Constructor
     * @param value value of rate
     * @param gridPoints number of grid points
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Size(double value, int gridPoints) {
        super(value, gridPoints);
        created++;
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
