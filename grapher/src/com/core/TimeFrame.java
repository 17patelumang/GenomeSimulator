/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.util.ArrayList;

/**
 * This class holds a period of time, with all the information required to compute
 * coalescent probabilities within it.
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class TimeFrame {

	/**
	 * @param MAX_TIME 
	 */
    static final int MAX_TIME = 10000;
    /**
     * @param Gstart start generation
     * @param Gend end generation
     */
    Generation Gstart, Gend;
    /**
     * @param migration migrationmatrix
     */
    MigrationMatrix migration;
    /**
     * @param transitions transition matrix
     */
    ArrayList<MigrationMatrix> transitions;
    int intStart, intEnd;
/**
 * 
 * @param Gstart start generation
 * @param Gend end generation
 * @param migration migration matrix
 */
    public TimeFrame(Generation Gstart, Generation Gend, MigrationMatrix migration) 
    {
        this.Gstart = Gstart;
        this.Gend = Gend;
        this.migration = migration;
        this.intStart = (int) Math.round(Gstart.value);
        this.intEnd = (Gend.value == Double.POSITIVE_INFINITY) ? TimeFrame.MAX_TIME : (int) Math.round(Gend.value);
    }

    /**
     * used to addTransition
     * @param transition matrix
     */
    public void addTransition(MigrationMatrix transition) {
        if (this.transitions == null) {
            this.transitions = new ArrayList<MigrationMatrix>();
        }
        this.transitions.add(transition);
    }

    /**
     * used to compute coalese vector 
     * @param initialState initial state of martix
     * @return
     * @throws Exception
     */
    public Matrix[] computeCoalescenceVector(Matrix initialState) throws Exception {
        if (RealFinalDemographicLanguage.debug) {
            System.out.println("Start of " + intStart + " " + intEnd);
        }
        Matrix[] coalescence = new Matrix[intEnd - intStart];
        Edge[] populations = migration.populationsFrom;
        Double[] sizes = new Double[populations.length];
        for (int g = 1; g <= (intEnd - intStart) - 1; g++) {
            initialState.multiplyMatrices(migration.getMigMatrix());
            for (int i = 0; i < populations.length; i++) {
                sizes[i] = populations[i].getSizeAt(intStart + g);
                if (RealFinalDemographicLanguage.debug) {
                    System.out.println("gen " + (intStart + g) + " pop " + populations[i].id + " size " + sizes[i]);
                }
            }
            coalescence[g - 1] = Matrix.multiplyDiagSelf(initialState, sizes);
        }
        initialState.multiplyMatrices(migration.getMigMatrix());
        for (int i = 0; i < populations.length; i++) {
            sizes[i] = populations[i].getSizeAt(Gend.value);
        }
        if (transitions != null) {
            for (int i = 0; i < transitions.size(); i++) {
                initialState.multiplyMatrices(transitions.get(i).migMat);
            }
            populations = transitions.get(transitions.size() - 1).populationsTo;
            sizes = new Double[populations.length];
            for (int i = 0; i < populations.length; i++) {
                sizes[i] = populations[i].getSizeAt(intEnd);
                if (RealFinalDemographicLanguage.debug) {
                    System.out.println("gen " + (intEnd) + " pop " + populations[i].id + " size " + sizes[i]);
                }
            }
        }
        coalescence[(intEnd - intStart) - 1] = Matrix.multiplyDiagSelf(initialState, sizes);
        return coalescence;
    }
}
