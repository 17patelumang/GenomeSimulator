/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grapher.processor;

/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */

import java.io.BufferedReader;
import java.io.FileReader;

import com.core.RealFinalDemographicLanguage;

public class FileProcessor {
	
	public RealFinalDemographicLanguage process(String filename) {
		try {
			FileReader fstream = new FileReader(filename);
			RealFinalDemographicLanguage model = new RealFinalDemographicLanguage(
					new BufferedReader(fstream));
			model.checkConstraints();
			model.buildMigrationMatrices();
			model.narrate();				
			return model;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main (String[] args){
	//	FileProcessor fp = new FileProcessor();
	//	RealFinalDemographicLanguage rf = fp.process("realfinaldemographiclanguage/src/admixture.txt");
	//	System.out.println("asdfasdf");
	}
}
