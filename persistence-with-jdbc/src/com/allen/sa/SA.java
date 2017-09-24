package com.allen.sa;

import com.allen.template.Template;

/**
 * Class holding information on a SA.
 * 
 * @author Allen Qian
 */

public class SA extends Template implements Comparable<SA>{

	@Override
	public int compareTo(SA sa) {
		// TODO Auto-generated method stub
		
		if (super.getSa()==(double)0 || sa.getSa()==(double)0) {
			return 0;
		}
		double thisScore = super.getSa() * 0.80 + (super.getTotal()-super.getSa())/super.getSa() * 0.20 + 10;
		double saScore = sa.getSa() * 0.80 + (sa.getTotal()-sa.getSa())/sa.getSa() * 0.20 + 10;

		if (saScore > thisScore) {
			return -1;
		}
		if (saScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
