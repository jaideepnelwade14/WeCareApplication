package com.team3.wecare.models;

public class AdminModel extends ContactDetailsModel{
	
	private String adminName;
	
	public AdminModel() {}
	
	public AdminModel(String email, String phone) {
		super(email, phone);
	}

	public AdminModel(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
