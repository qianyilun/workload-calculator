package com.allen.rtc;

import com.allen.QueueDays;
import com.allen.template.Template;

/**
 * Class holding information on a RTC.
 * 
 * @author Allen Qian
 */

public class RTC extends Template implements Comparable<RTC>{
	public double getPoint() {
		return super.getRtc() * 0.80 + (super.getSum()-super.getRtc())/super.getRtc() * 0.20 + 10;
		
	}
	
	@Override
	public int compareTo(RTC rtc) {
		// TODO Auto-generated method stub
		double thisScore = super.getSum() / QueueDays.hash.get(super.getName());
		double rtcScore = rtc.getSum() / QueueDays.hash.get(rtc.getName());
		
		if (rtcScore > thisScore) {
			return -1;
		}
		if (rtcScore < thisScore) {
			return 1;
		}
		return 0;
	}
}
