package com.allen.pcm;

import com.allen.template.Template;

public class PCM extends Template implements Comparable<PCM>{

	@Override
	public int compareTo(PCM pcm) {
		// TODO Auto-generated method stub
		double thisScore = super.getAmount() * 0.80 + (super.getTotal()-super.getAmount())/super.getAmount() * 0.20 + 10;
		double pcmScore = pcm.getAmount() * 0.80 + (pcm.getTotal()-pcm.getAmount())/pcm.getAmount() * 0.20 + 10;

		if (pcmScore > thisScore) {
			return -1;
		}
		if (pcmScore < thisScore) {
			return 1;
		}
		return 0;
	}
	
}
