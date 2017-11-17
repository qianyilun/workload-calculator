package com.allen.pcm;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a PCM.
 * 
 * @author Allen Qian
 */

public class PCM extends Template implements Comparable<PCM>{
	public double getPoint() {
		return super.getPcm() * 0.80 + (super.getSum()-super.getPcm())/super.getPcm() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(PCM pcm) {
		// TODO Auto-generated method stub
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double pcmScore = pcm.getSum() / QueueDays.hash.get(pcm.getName());
		
		if (pcmScore > thisScore) {
			return -1;
		}
		if (pcmScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
