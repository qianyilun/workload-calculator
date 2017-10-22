package com.allen.fc;

import com.allen.template.Template;

/**
 * Class holding information on a FC.
 * 
 * @author Allen Qian
 */

public class FC extends Template implements Comparable<FC>{
	private double point;

	public double getPoint() {
		return point = super.getFc() * 0.80 + (super.getSum()-super.getFc())/super.getFc() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(FC fc) {
		// TODO Auto-generated method stub
		if (this.getSum() < fc.getSum()) {
			return -1;
		} else if (this.getSum() > fc.getSum()) {
			return 1;
		} 
		
		if (super.getFc()==(double)0 || fc.getFc()==(double)0) {
			return 0;
		}
		double thisScore = super.getFc() * 0.80 + (super.getSum()-super.getFc())/super.getFc() * 0.20 + 10;
		double fcScore = fc.getFc() * 0.80 + (fc.getSum()-fc.getFc())/fc.getFc() * 0.20 + 10;

		if (fcScore > thisScore) {
			return -1;
		}
		if (fcScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
