/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Migration matrix for a set of populations, based on current values for migration rates
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class MigrationMatrix {

    // array of populations represented in the matrix
    final Edge[] populationsFrom, populationsTo;
    // number of populations in matrix
    final int numPopIn, numPopOut;
    // the matrix
    Matrix migMat;
    TreeMap<Rate, TreeSet<Integer>> treeIndex = new TreeMap<Rate, TreeSet<Integer>>();
    // from index
    HashMap<String, Integer> populationIndexIn = new HashMap<String, Integer>();
    // to index
    HashMap<String, Integer> populationIndexOut;

    /**
     * Constructor
     * @param edges set of active edges
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public MigrationMatrix(TreeSet<Edge> edges) {
        this.populationsFrom = edges.toArray(new Edge[edges.size()]);
        this.populationsTo = populationsFrom;
        for (int i = 0; i < edges.size(); i++) {
            this.populationIndexIn.put(populationsFrom[i].id, i);
        }
        this.populationIndexOut = this.populationIndexIn;
        this.numPopIn = this.populationsFrom.length;
        this.numPopOut = this.numPopIn;
        Double[][] migMat = new Double[numPopIn][numPopOut];
        // for each population, fill in entries in matrix.
        for (int i = 0; i < this.numPopIn; i++) 
        {
            // current population
            Edge e = this.populationsFrom[i];
            // initialize chance of staying to 1.0
            migMat[i][i] = 1.0;
            // all elements before diagonal on this row
            for (int j = 0; j < i; j++)
            {
                // if a rate is specified, use it and remove it from chance of staying
                if (e.getMigOut().containsKey(populationsFrom[j])) 
                {
                    Rate r = e.getMigOut().get(populationsFrom[j]).r;
                    migMat[i][j] = r.value;
                    migMat[i][i] -= migMat[i][j];
                    // add i to places where rate r appears
                    TreeSet<Integer> t = (treeIndex.containsKey(r)) ? treeIndex.get(r)
                            : new TreeSet<Integer>();
                    t.add(i);
                    treeIndex.put(r, t);
                } 
                else 
                {
                    // otherwise assume it's 0.0
                    migMat[i][j] = 0.0;
                }
            }
            // all elements after diagonal
            for (int j = i + 1; j < this.numPopOut; j++) 
            {
                // if a rate is specified, use it and remove it from chance of staying
                if (e.getMigOut().containsKey(populationsFrom[j])) 
                {
                    migMat[i][j] = e.getMigOut().get(populationsFrom[j]).r.value;
                    migMat[i][i] -= migMat[i][j];
                } 
                else 
                {
                    // otherwise assume it's 0.0
                    migMat[i][j] = 0.0;
                }
            }
        }
        this.migMat = new Matrix(migMat);
    }

    /**
     * Constructor of transition matrix (from - to)
     * @param edgesIn set of active edges in input
     * @param edgesOut set of active edges in output
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public MigrationMatrix(TreeSet<Edge> edgesIn, TreeSet<Edge> edgesUnchanged) throws Exception {
        // setup input edges
        TreeSet<Edge> tempEdges = new TreeSet<Edge>();
        tempEdges.addAll(edgesIn);
        tempEdges.addAll(edgesUnchanged);
        this.populationsFrom = tempEdges.toArray(new Edge[tempEdges.size()]);
        // create index for input populations
        for (int i = 0; i < tempEdges.size(); i++) 
        {
            this.populationIndexIn.put(populationsFrom[i].id, i);
//            System.out.println("From " + populationsFrom[i].id);
        }
        this.numPopIn = this.populationsFrom.length;
        tempEdges.clear();
        // setup output edges
        for (Edge e : edgesIn) 
        {
            tempEdges.addAll(e.n2.outEdges);
        }
        tempEdges.addAll(edgesUnchanged);
        this.populationsTo = tempEdges.toArray(new Edge[tempEdges.size()]);
        // create index for output populations
        this.populationIndexOut = new HashMap<String, Integer>();
        for (int i = 0; i < tempEdges.size(); i++) 
        {
            this.populationIndexOut.put(populationsTo[i].id, i);
//            System.out.println("To " + populationsTo[i].id);
        }
        this.numPopOut = this.populationsTo.length;
        // create migration matrix
        Double[][] migMat = new Double[numPopIn][numPopOut];
        // for each population, fill in entries in matrix.
        for (int i = 0; i < this.numPopIn; i++) {
            for (int j = 0; j < this.numPopOut; j++) {
                migMat[i][j] = 0.0;
            }
        }
        // these stay where they were (1.0 prob)
        for (Edge e : edgesUnchanged)
        {
            int indexIn = this.populationIndexIn.get(e.id);
            int indexOut = this.populationIndexOut.get(e.id);
            migMat[indexIn][indexOut] = 1.0;
        }
        // these might move if there are multiple output populations in the landing node
        for (Edge e : edgesIn)
        {
            int indexSelf = this.populationIndexIn.get(e.id);
            // sum of sizes of output node
            double sum = e.n2.getSumOfOutGoing();
            for (Edge eOut : e.n2.outEdges)
            {
                int indexTo = this.populationIndexOut.get(eOut.id);
                // proportion of size of each subpop = prob moving there
                double prob = eOut.s1.value / sum;
                migMat[indexSelf][indexTo] = prob;
            }
        }
        this.migMat = new Matrix(migMat);
    }

    /**
     * returns the migration matrix
     * @return migration matrix
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public Matrix getMigMatrix() {
        return migMat;
    }

    /**
     * returns the number of populations in input the matrix
     * @return the number of populations in output the matrix
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public int getNumPopIn() {
        return numPopIn;
    }

    /**
     * returns the number of populations in output the matrix
     * @return the number of populations in output the matrix
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    public int getNumPopOut() {
        return numPopOut;
    }

    /**
     * returns printable string version
     * @return string representing matrix
     * @author Pier Palamara <pier@cs.columbia.edu>
     */
    @Override
    public String toString() {
        String matrixString = "[";
        matrixString += populationsFrom[0].id;
        for (int i = 1; i < this.populationsFrom.length; i++) {
            matrixString += ", " + this.populationsFrom[i].id;
        }
        matrixString += "] -> [" + populationsTo[0].id;
        for (int i = 1; i < this.populationsTo.length; i++) {
            matrixString += ", " + this.populationsTo[i].id;
        }
        matrixString += "]";
        matrixString += "\n[";
        for (int i = 0; i < this.numPopIn; i++) {
            matrixString += "\t" + this.migMat.mat[i][0];
            for (int j = 1; j < this.numPopOut; j++) {
                matrixString += ",\t" + this.migMat.mat[i][j];
            }
            if (i != this.numPopIn - 1) {
                matrixString += "\n";
            } else {
                matrixString += "\t]";
            }
        }
        return matrixString;
    }
}
