package com.allen.dsm;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a DSM.
 * 
 * @author Allen Qian
 */

public class DSM extends Template implements Comparable<DSM>{
	public double getPoint() {
		return super.getDsm() * 0.80 + (super.getSum()-super.getDsm())/super.getDsm() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(DSM dsm) {
		// TODO Auto-generated method stub
//		if (this.getSum() < dsm.getSum()) {
//			return -1;
//		} else if (this.getSum() > dsm.getSum()) {
//			return 1;
//		} 
//		
//		if (super.getDsm()==(double)0 || dsm.getDsm()==(double)0) {
//			return 0;
//		}
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double dsmScore = dsm.getSum() / QueueDays.hash.get(dsm.getName());

		if (dsmScore > thisScore) {
			return -1;
		}
		if (dsmScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
