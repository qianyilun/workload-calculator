package com.allen.sm;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a SM.
 * 
 * @author Allen Qian
 */

public class SM extends Template implements Comparable<SM>{
	public double getPoint() {
		return super.getSm() * 0.80 + (super.getSum()-super.getSm())/super.getSm() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(SM sm) {
		// TODO Auto-generated method stub
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double smScore = sm.getSum() / QueueDays.hash.get(sm.getName());
		
		if (smScore > thisScore) {
			return -1;
		}
		if (smScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
