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
		
		if (super.getDsm()==(double)0 || dsm.getDsm()==(double)0) {
			return 0;
		}
		double thisScore = super.getDsm() * 0.80 + (super.getTotal()-super.getDsm())/super.getDsm() * 0.20 + 10;
		double dsmScore = dsm.getDsm() * 0.80 + (dsm.getTotal()-dsm.getDsm())/dsm.getDsm() * 0.20 + 10;

		if (dsmScore > thisScore) {
			return -1;
		}
		if (dsmScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
