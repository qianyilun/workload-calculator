package com.allen.nw;

import com.sap.cloud.sample.persistence.Person;

public class NW implements Comparable<NW>{
	private int id;
	private int amount;
	private int total;
	private String name;
	private boolean hide; 
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	/*@Override
	public int compareTo(NW nw) {
		// TODO Auto-generated method stub
		double thisScore = this.amount * 0.2 + this.total * 0.8;
		double nwScore = nw.amount * 0.2 + nw.total * 0.8;
		
		System.out.println(thisScore + "/"+ nwScore);
		if (nwScore > thisScore) {
			return -1;
		}
		if (nwScore < thisScore) {
			return 1;
		}
		return 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}*/
	
	@Override
	public int compareTo(NW nw) {
		// TODO Auto-generated method stub
		double thisScore = this.amount * 0.80 + (this.total-this.amount)/this.amount * 0.20 + 10;
		double nwScore = nw.amount * 0.80 + (nw.total-nw.amount)/nw.amount * 0.20 + 10;

		if (nwScore > thisScore) {
			return -1;
		}
		if (nwScore < thisScore) {
			return 1;
		}
		return 0;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
