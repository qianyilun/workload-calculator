package com.allen.nw;

import com.sap.cloud.sample.persistence.Person;

public class NW {
	private Person person;
	private int amount;
	private int total;
	private String firstName;
	private String lastName;
	
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
	
	
}
