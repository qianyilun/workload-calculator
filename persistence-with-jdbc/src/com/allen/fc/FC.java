package com.allen.fc;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a FC.
 * 
 * @author Allen Qian
 */

public class FC extends Template implements Comparable<FC>{
	public double getPoint() {
		return super.getFc() * 0.80 + (super.getSum()-super.getFc())/super.getFc() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(FC fc) {
		// TODO Auto-generated method stub
//		if (this.getSum() < fc.getSum()) {
//			return -1;
//		} else if (this.getSum() > fc.getSum()) {
//			return 1;
//		} 
//		
//		if (super.getFc()==(double)0 || fc.getFc()==(double)0) {
//			return 0;
//		}
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double fcScore = fc.getSum() / QueueDays.hash.get(fc.getName());
		
		if (fcScore > thisScore) {
			return -1;
		}
		if (fcScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
