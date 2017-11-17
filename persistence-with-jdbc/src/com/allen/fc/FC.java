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
		double thisScore;
		double fcScore;
		
		if (super.getName().equals("Yvonne")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.75);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (fc.getName().equals("Yvonne")) {
			fcScore = fc.getSum() / (QueueDays.hash.get(fc.getName())*0.75);
		} else {
			fcScore = fc.getSum() / QueueDays.hash.get(fc.getName());
		}
		
		if (fcScore > thisScore) {
			return -1;
		}
		if (fcScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
=======
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
		double thisScore;
		double fcScore;
		
		if (super.getName().equals("Yvonne")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.75);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (fc.getName().equals("Yvonne")) {
			fcScore = fc.getSum() / (QueueDays.hash.get(fc.getName())*0.75);
		} else {
			fcScore = fc.getSum() / QueueDays.hash.get(fc.getName());
		}
		
		if (fcScore > thisScore) {
			return -1;
		}
		if (fcScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
