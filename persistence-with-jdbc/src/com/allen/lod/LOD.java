package com.allen.lod;

import com.allen.template.Template;

public class LOD extends Template implements Comparable<LOD>{

	@Override
	public int compareTo(LOD lod) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double lodScore = lod.getAmount() * 0.80 + (lod.getTotal()-lod.getAmount())/lod.getAmount() * 0.20 + 10;

		if (lodScore > thisScore) {
			return -1;
		}
		if (lodScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
