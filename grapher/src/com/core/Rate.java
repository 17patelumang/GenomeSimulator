/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.core;

import java.util.ArrayList;

/**
 * Rate parameter.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Rate extends Parameter {

    /**
     * @param min max lower bound, upper bound
     */
    Double min = 0.0, max = 1.0;
    /**
     * @param R0 R1 constants, 0 and 1, used if no rate is specified for two populations
     */
    static Rate R0, R1;
    /**
     * @param created counter of created objects
     */
    static int created = 0;
    /**
     * @param matricesAppearsIn matrices this rate appears in
     */
    private ArrayList<MigrationMatrix> matricesAppearsIn = new ArrayList<MigrationMatrix>();

    /**
     * Constructor
     * @param value value of rate
     * @param gridPoints number of grid points
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Rate(double value, int gridPoints) {
        super(value, gridPoints);
        created++;
    }
    
    /**
     * add a migration matrix this rate appears in, to keep track of constraints
     * @param m the migration matrix
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public void addMatrix(MigrationMatrix m) {
        matricesAppearsIn.add(m);
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
