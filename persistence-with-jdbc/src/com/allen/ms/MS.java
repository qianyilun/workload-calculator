package com.allen.ms;

import com.allen.template.Template;

/**
 * Class holding information on a MS.
 * 
 * @author Allen Qian
 */

public class MS extends Template implements Comparable<MS>{

	@Override
	public int compareTo(MS ms) {
		// TODO Auto-generated method stub
		if (this.getSum() < ms.getSum()) {
			return -1;
		} else if (this.getSum() > ms.getSum()) {
			return 1;
		} 
		
		if (super.getMs()==(double)0 || ms.getMs()==(double)0) {
			return 0;
		}
		double thisScore = super.getMs() * 0.80 + (super.getTotal()-super.getMs())/super.getMs() * 0.20 + 10;
		double msScore = ms.getMs() * 0.80 + (ms.getTotal()-ms.getMs())/ms.getMs() * 0.20 + 10;

		if (msScore > thisScore) {
			return -1;
		}
		if (msScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
