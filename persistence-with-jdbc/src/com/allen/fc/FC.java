package com.allen.fc;

import com.allen.template.Template;

public class FC extends Template implements Comparable<FC>{

	@Override
	public int compareTo(FC fc) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double fcScore = fc.getAmount() * 0.80 + (fc.getTotal()-fc.getAmount())/fc.getAmount() * 0.20 + 10;

		if (fcScore > thisScore) {
			return -1;
		}
		if (fcScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
