/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

/**
 *
 * @author Pier Palamara <pier@cs.columbia.edu>
 */
public class Matrix {
	/**
	 * @param id gives name to Matrix object
	 */
    String id;
    /**
     * @param Double n*n matrix
     */
    Double[][] mat;
    
    /**
     * 
     * @param N identity matrix of dimension N 
     */

    public Matrix(int N) {
        mat = getIdentity(N);
    }
    /**
     * 
     * @param D Double matrix
     */
    public Matrix(Double[][] D) {
        this.mat = D;
    }
    /**
     * 
     * @param d 
     */
    public Matrix(double[][] d) {
        Double[][] D = new Double[d.length][d[0].length];
        for (int i = 0; i < d.length; i++)
        {
            for (int j = 0; j<d.length; j++)  
            {
                D[i][j] = d[i][j];
            }
        }
        this.mat = D;
    }
    /**
     * 
     * @param id name of Identity martix
     * @param N dimension
     */
    public Matrix(String id, int N) {
        this.id = id;
        mat = getIdentity(N);
    }

    /**
     * prints the matrix
     */
    public void printMat() {
        if (id != null) {
            System.out.println(id);
        }
        for (int k = 0; k < mat.length; k++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[k][j] + "\t");
            }
            System.out.println("");
        }
    }

    /**
     * 
     * @param mat matrix to be printed
     */
    public static void printMat(double[][] mat) {
        for (int k = 0; k < mat.length; k++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[k][j] + "\t");
            }
            System.out.println("");
        }
    }

    /**
     * gives row sum
     */
    public void printRowSum() {
        if (id != null) {
            System.out.println(id);
        }
        for (int k = 0; k < mat.length; k++) {
            double tot = 0;
            for (int j = 0; j < mat[0].length; j++) {
                tot += mat[k][j];
            }
            System.out.println(k + " -> " + tot);
        }
    }

    /**
     * 
     * @param mat2 matrix to be multiplied 
     * @throws Exception
     */
    public void multiplyMatrices(Matrix mat2) throws Exception {
//        this.printMat();
//        mat2.printMat();
//        System.out.println(mat.length + "x" + mat[0].length + " " + mat2.mat.length + "x" + mat2.mat[0].length);
        Double[][] B = mat2.mat;
        if (mat[0].length != B.length) {
            throw new Exception("inconsistent size of matrices in multiplication: " + mat.length + "x" + mat[0].length + " and " + B.length + "x" + B[0].length);
        }
        Double[][] res = new Double[mat.length][B[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                res[i][j] = 0.0;
                for (int k = 0; k < mat[0].length; k++) {
                    res[i][j] += mat[i][k] * B[k][j];
                }
            }
        }
        mat = res;
    }
    
    /**
     * 
     * @param mat2 matrix to be summed 
     * @throws Exception
     */
    public void sumToMatrix(Matrix mat2) throws Exception {
//        printMat(A);
//        printMat(B);
        Double[][] B = mat2.mat;
        if (mat.length != B.length || mat[0].length != B[0].length) {
            throw new Exception("inconsistent size of matrices in summation: " + mat.length + "x" + mat[0].length + " and " + B.length + "x" + B[0].length);
        }
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j] += mat2.mat[i][j];
            }
        }
    }

  
    /**
     * multiply M*N*M', where N is diagonal with elements from array diag
     * @param matrix
     * @param diag
     * @return
     * @throws Exception
     */
    public static Matrix multiplyDiagSelf(Matrix matrix, Double[] diag) throws Exception {
        Double[][] mat = matrix.mat;
        Double[][] res = new Double[mat.length][mat.length];
        if (diag.length != mat[0].length) {
            throw new Exception("size of diagonal matrix does not match #columns of matrix: " + diag.length + " and " + mat[0].length);
        }
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                res[i][j] = 0.0;
                for (int k = 0; k < mat[0].length; k++) {
                    res[i][j] += mat[i][k] / diag[k] * mat[j][k];
                }
            }
        }
        return new Matrix(res);
    }

    /**
     * 
     * @param N dimension of Identity matrix
     * @return ID N*N matrix
     */
    public static Double[][] getIdentity(int N) {
        Double[][] ID = new Double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ID[i][j] = (i == j) ? 1.0 : 0.0;
            }
        }
        return ID;
    }

    /**
     * 
     * @param N N*N zero matrix
     * @return
     */
    public static Double[][] getZeroes(int N) {
        Double[][] ID = new Double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ID[i][j] = 0.0;
            }
        }
        return ID;
    }

    /**
     * 
     * @param v imput scalar
     */
    public void sumScalar(double v) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j] += v;
            }
        }
    }

    /**
     * gives transpost of matrix
     * @return
     */
    public Matrix getTranspose() {
        Matrix res = new Matrix(new Double[mat[0].length][mat.length]);
        for (int i = 0; i < mat[0].length; i++) {
            for (int j = 0; j < mat.length; j++) {
                res.mat[i][j] = mat[j][i];
            }
        }
        return res;
    }
}
