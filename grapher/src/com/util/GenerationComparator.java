package com.util;

/**
 * @author Umang Patel<ujp2001@columbia.edu>
 */


import java.util.Comparator;

import com.core.Generation;

public class GenerationComparator implements Comparator<Generation> {

	@Override
	public int compare(Generation g1, Generation g2) {
		// TODO Auto-generated method stub
		return g1.getValue().compareTo(g2.getValue());
	}

}
