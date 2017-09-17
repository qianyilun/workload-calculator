package com.allen.sa;

import com.allen.template.Template;

public class SA extends Template implements Comparable<SA>{

	@Override
	public int compareTo(SA sa) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double saScore = sa.getAmount() * 0.80 + (sa.getTotal()-sa.getAmount())/sa.getAmount() * 0.20 + 10;

		if (saScore > thisScore) {
			return -1;
		}
		if (saScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
