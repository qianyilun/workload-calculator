package com.allen.fc;

import com.allen.template.Template;

/**
 * Class holding information on a FC.
 * 
 * @author Allen Qian
 */

public class FC extends Template implements Comparable<FC>{

	@Override
	public int compareTo(FC fc) {
		// TODO Auto-generated method stub
		
		if (super.getFc()==(double)0 || fc.getFc()==(double)0) {
			return 0;
		}
		double thisScore = super.getFc() * 0.80 + (super.getTotal()-super.getFc())/super.getFc() * 0.20 + 10;
		double fcScore = fc.getFc() * 0.80 + (fc.getTotal()-fc.getFc())/fc.getFc() * 0.20 + 10;

		if (fcScore > thisScore) {
			return -1;
		}
		if (fcScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
