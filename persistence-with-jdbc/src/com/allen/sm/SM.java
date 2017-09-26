package com.allen.sm;

import com.allen.template.Template;

/**
 * Class holding information on a SM.
 * 
 * @author Allen Qian
 */

public class SM extends Template implements Comparable<SM>{

	@Override
	public int compareTo(SM sm) {
		// TODO Auto-generated method stub
		if (this.getSum() < sm.getSum()) {
			return -1;
		} else if (this.getSum() > sm.getSum()) {
			return 1;
		} 
		
		if (super.getSm()==(double)0 || sm.getSm()==(double)0) {
			return 0;
		}
		double thisScore = super.getSm() * 0.80 + (super.getSum()-super.getSm())/super.getSm() * 0.20 + 10;
		double smScore = sm.getSm() * 0.80 + (sm.getSum()-sm.getSm())/sm.getSm() * 0.20 + 10;

		if (smScore > thisScore) {
			return -1;
		}
		if (smScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
