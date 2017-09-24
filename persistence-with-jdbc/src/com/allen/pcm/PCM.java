package com.allen.pcm;

import com.allen.template.Template;

/**
 * Class holding information on a PCM.
 * 
 * @author Allen Qian
 */

public class PCM extends Template implements Comparable<PCM>{

	@Override
	public int compareTo(PCM pcm) {
		// TODO Auto-generated method stub
		
		if (super.getPcm()==(double)0 || pcm.getPcm()==(double)0) {
			return 0;
		}
		double thisScore = super.getPcm() * 0.80 + (super.getTotal()-super.getPcm())/super.getPcm() * 0.20 + 10;
		double pcmScore = pcm.getPcm() * 0.80 + (pcm.getTotal()-pcm.getPcm())/pcm.getPcm() * 0.20 + 10;

		if (pcmScore > thisScore) {
			return -1;
		}
		if (pcmScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
