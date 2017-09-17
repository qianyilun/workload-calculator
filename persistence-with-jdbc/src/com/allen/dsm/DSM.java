package com.allen.dsm;

import com.allen.template.Template;

/**
 * Class holding information on a DSM.
 * 
 * @author Allen Qian
 */

public class DSM extends Template implements Comparable<DSM>{

	@Override
	public int compareTo(DSM dsm) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double dsmScore = dsm.getAmount() * 0.80 + (dsm.getTotal()-dsm.getAmount())/dsm.getAmount() * 0.20 + 10;

		if (dsmScore > thisScore) {
			return -1;
		}
		if (dsmScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
