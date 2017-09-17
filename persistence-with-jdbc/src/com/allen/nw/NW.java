package com.allen.nw;

import com.allen.template.Template;

/**
 * Class holding information on a NW.
 * 
 * @author Allen Qian
 */

public class NW extends Template implements Comparable<NW>{

	@Override
	public int compareTo(NW nw) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double nwScore = nw.getAmount() * 0.80 + (nw.getTotal()-nw.getAmount())/nw.getAmount() * 0.20 + 10;

		if (nwScore > thisScore) {
			return -1;
		}
		if (nwScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
