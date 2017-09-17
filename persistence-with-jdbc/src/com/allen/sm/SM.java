package com.allen.sm;

import com.allen.ms.MS;
import com.allen.template.Template;

public class SM extends Template implements Comparable<SM>{

	@Override
	public int compareTo(SM sm) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double smScore = sm.getAmount() * 0.80 + (sm.getTotal()-sm.getAmount())/sm.getAmount() * 0.20 + 10;

		if (smScore > thisScore) {
			return -1;
		}
		if (smScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
