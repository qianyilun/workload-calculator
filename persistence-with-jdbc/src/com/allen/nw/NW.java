package com.allen.nw;

import com.sap.cloud.sample.persistence.Person;

public class NW implements Comparable<NW>{
	private Person person;
	private String id;
	private int amount;
	private int total;
	private String firstName;
	private String lastName;
	private boolean hide; 
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson() {
		person = new Person();
		person.setFirstName(firstName.trim());
        person.setLastName(lastName.trim());
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	@Override
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
	}
	
	
}
