package com.allen.lod;

import com.allen.QueueDays;
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
		double thisScore;
		double lodScore;
		
		if (super.getName().equals("John L")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.5);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (lod.getName().equals("John L")) {
			lodScore = lod.getSum() / (QueueDays.hash.get(lod.getName())*0.5);
		} else {
			lodScore = lod.getSum() / QueueDays.hash.get(lod.getName());
		}
		
		if (lodScore > thisScore) {
			return -1;
		}
		if (lodScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
=======
package com.allen.lod;

import com.allen.QueueDays;
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
		double thisScore;
		double lodScore;
		
		if (super.getName().equals("John L")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.5);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (lod.getName().equals("John L")) {
			lodScore = lod.getSum() / (QueueDays.hash.get(lod.getName())*0.5);
		} else {
			lodScore = lod.getSum() / QueueDays.hash.get(lod.getName());
		}
		
		if (lodScore > thisScore) {
			return -1;
		}
		if (lodScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
