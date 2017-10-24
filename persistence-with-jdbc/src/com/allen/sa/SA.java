package com.allen.sa;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a SA.
 * 
 * @author Allen Qian
 */

public class SA extends Template implements Comparable<SA>{
	public double getPoint() {
		return super.getSa() * 0.80 + (super.getSum()-super.getSa())/super.getSa() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(SA sa) {
		// TODO Auto-generated method stub
//		if (this.getSum() < sa.getSum()) {
//			return -1;
//		} else if (this.getSum() > sa.getSum()) {
//			return 1;
//		} 
//		
//		if (super.getSa()==(double)0 || sa.getSa()==(double)0) {
//			return 0;
//		}
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double saScore = sa.getSum() / QueueDays.hash.get(sa.getName());
		
		if (saScore > thisScore) {
			return -1;
		}
		if (saScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
