package com.team3.wecare.models;

import com.team3.wecare.entities.Jurisdiction;

public class JurisdictionModel {
	private int juryId;
	private String area;
	private String ward;
	private String layout;

	public JurisdictionModel() {
	}

	public JurisdictionModel(String area, String ward, String layout) {
		this.area = area;
		this.ward = ward;
		this.layout = layout;
	}

	public JurisdictionModel(Jurisdiction jury) {
		this.juryId = jury.getJuryId();
		this.area = jury.getArea();
		this.ward = jury.getWard();
		this.layout = jury.getLayout();
	}

	public int getJuryId() {
		return juryId;
	}

	public void setJuryId(int juryId) {
		this.juryId = juryId;
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
	
}