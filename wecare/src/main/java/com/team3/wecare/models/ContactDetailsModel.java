package com.team3.wecare.models;

public class ContactDetailsModel {

	protected String email;
	protected String phone;

	public ContactDetailsModel() {
	}

	public ContactDetailsModel(String email, String phone) {
		this.email = email;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
