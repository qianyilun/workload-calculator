package com.allen.lod;

import com.allen.template.Template;

/**
 * Class holding information on a LOD.
 * 
 * @author Allen Qian
 */

public class LOD extends Template implements Comparable<LOD>{
	public double getPoint() {
		return super.getLod() * 0.80 + (super.getSum()-super.getLod())/super.getLod() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(LOD lod) {
		// TODO Auto-generated method stub
		if (this.getSum() < lod.getSum()) {
			return -1;
		} else if (this.getSum() > lod.getSum()) {
			return 1;
		} 
		
		if (super.getLod()==(double)0 || lod.getLod()==(double)0) {
			return 0;
		}
		double thisScore = super.getLod() * 0.80 + (super.getSum()-super.getLod())/super.getLod() * 0.20 + 10;
		double lodScore = lod.getLod() * 0.80 + (lod.getSum()-lod.getLod())/lod.getLod() * 0.20 + 10;

		if (lodScore > thisScore) {
			return -1;
		}
		if (lodScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
