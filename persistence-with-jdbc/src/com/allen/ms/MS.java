package com.allen.ms;

import com.allen.template.Template;

public class MS extends Template implements Comparable<MS>{

	@Override
	public int compareTo(MS ms) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double MSScore = ms.getAmount() * 0.80 + (ms.getTotal()-ms.getAmount())/ms.getAmount() * 0.20 + 10;

		if (MSScore > thisScore) {
			return -1;
		}
		if (MSScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
