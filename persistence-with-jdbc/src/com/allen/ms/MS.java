package com.allen.ms;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a MS.
 * 
 * @author Allen Qian
 */

public class MS extends Template implements Comparable<MS>{
	public double getPoint() {
		return super.getMs() * 0.80 + (super.getSum()-super.getMs())/super.getMs() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(MS ms) {
		// TODO Auto-generated method stub
		double thisScore;
		double msScore;
		
		if (super.getName().equals("John L")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.5);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (ms.getName().equals("John L")) {
			msScore = ms.getSum() / (QueueDays.hash.get(ms.getName())*0.5);
		} else {
			msScore = ms.getSum() / QueueDays.hash.get(ms.getName());
		}
		
		if (msScore > thisScore) {
			return -1;
		}
		if (msScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
=======
package com.allen.ms;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a MS.
 * 
 * @author Allen Qian
 */

public class MS extends Template implements Comparable<MS>{
	public double getPoint() {
		return super.getMs() * 0.80 + (super.getSum()-super.getMs())/super.getMs() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(MS ms) {
		// TODO Auto-generated method stub
		double thisScore;
		double msScore;
		
		if (super.getName().equals("John L")) {
			thisScore = super.getSum() / (QueueDays.hash.get(super.getName())*0.5);
		} else {
			thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		}
		
		if (ms.getName().equals("John L")) {
			msScore = ms.getSum() / (QueueDays.hash.get(ms.getName())*0.5);
		} else {
			msScore = ms.getSum() / QueueDays.hash.get(ms.getName());
		}
		
		if (msScore > thisScore) {
			return -1;
		}
		if (msScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
