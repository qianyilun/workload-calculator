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
		
		if (super.getNw()==(double)0 || nw.getNw()==(double)0) {
			return 0;
		}
		double thisScore = super.getNw() * 0.80 + (super.getTotal()-super.getNw())/super.getNw() * 0.20 + 10;
		double nwScore = nw.getNw() * 0.80 + (nw.getTotal()-nw.getNw())/nw.getNw() * 0.20 + 10;

		if (nwScore > thisScore) {
			return -1;
		}
		if (nwScore < thisScore) {
			return 1;
		}
		return 0;
	}


	
}
