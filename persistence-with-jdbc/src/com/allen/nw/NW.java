package com.allen.nw;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a NW.
 * 
 * @author Allen Qian
 */

public class NW extends Template implements Comparable<NW>{
	public double getPoint() {
		return super.getNw() * 0.80 + (super.getSum()-super.getNw())/super.getNw() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(NW nw) {
		// TODO Auto-generated method stub	
		double thisScore;
		double nwScore;
		
		if (super.getName().equals("Yvonne")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.75);
		} else if (super.getName().equals("John L")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.5);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (nw.getName().equals("Yvonne")) {
			nwScore = nw.getSum() / (QueueDays.hash.get(nw.getName())*0.75);
		} else if (nw.getName().equals("John L")) {
			nwScore = nw.getSum() / (QueueDays.hash.get(nw.getName())*0.5);
		} else {
			nwScore = nw.getSum() / QueueDays.hash.get(nw.getName());
		}
		
		if (nwScore > thisScore) {
			return -1;
		}
		if (nwScore < thisScore) {
			return 1;
		}
		return 0;
	}


	
}
