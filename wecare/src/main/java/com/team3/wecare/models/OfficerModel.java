package com.team3.wecare.models;

import com.team3.wecare.entities.Officer;

public class OfficerModel extends ContactDetailsModel {

	private int officerId;
	private String area;
	private String ward;
	private String layout;
	private String officerName;
	private String address;

	public OfficerModel() {
	}

	public OfficerModel(String officerName, String address) {
		this.officerName = officerName;
		this.address = address;
	}

	public OfficerModel(String area, String ward, String layout, String officerName, String address) {
		this.area = area;
		this.ward = ward;
		this.layout = layout;
		this.officerName = officerName;
		this.address = address;
	}

	public OfficerModel(Officer officer) {
		super(officer.getEmail(), officer.getPhone());
		this.officerId = officer.getOfficerId();
		this.officerName = officer.getOfficerName();
		this.address = officer.getAddress();

		if (officer.getJuryOfficer() != null) {
			this.area = officer.getJuryOfficer().getArea();
			this.ward = officer.getJuryOfficer().getWard();
			this.layout = officer.getJuryOfficer().getLayout();
		} else {
			this.area = "N/A";
			this.ward = "N/A";
			this.layout = "N/A";
		}
	}

	public int getOfficerId() {
		return officerId;
	}

	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getOfficerName() {
		return officerName;
	}

	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}