package com.allen.ms;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a MS.
 * 
 * @author Allen Qian
 */

public class MS extends Template implements Comparable<MS>{
	public double getPoint() {
		return super.getMs() * 0.80 + (super.getSum()-super.getMs())/super.getMs() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(MS ms) {
		// TODO Auto-generated method stub
//		if (this.getSum() < ms.getSum()) {
//			return -1;
//		} else if (this.getSum() > ms.getSum()) {
//			return 1;
//		} 
//		
//		if (super.getMs()==(double)0 || ms.getMs()==(double)0) {
//			return 0;
//		}
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double msScore = ms.getSum() / QueueDays.hash.get(ms.getName());
		
		if (msScore > thisScore) {
			return -1;
		}
		if (msScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
