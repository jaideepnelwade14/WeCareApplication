package com.team3.wecare.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Jurisdiction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int juryId;
	@Column(nullable = false, length = 30)
	private String area;
	@Column(nullable = false, length = 8)
	private String ward;
	@Column(nullable = false, length = 35)
	private String layout;
	@Column(nullable = false)
	private LocalDateTime registeredDate;
	private LocalDateTime modifiedDate;

	@OneToMany(targetEntity = Officer.class, cascade = {
			CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "juryOfficer")
	private List<Officer> officers;

	@OneToMany(targetEntity = Complaint.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "juryComplaint")
	private List<Complaint> complaints;

	public Jurisdiction() {
	}

	public Jurisdiction(int juryId, String area, String ward, String layout) {
		this.juryId = juryId;
		this.area = area;
		this.ward = ward;
		this.layout = layout;
	}

	public Jurisdiction(String area, String ward, String layout, LocalDateTime registeredDate,
			LocalDateTime modifiedDate) {
		this.area = area;
		this.ward = ward;
		this.layout = layout;
		this.registeredDate = registeredDate;
		this.modifiedDate = modifiedDate;
	}

	public Jurisdiction(int juryId, String area, String ward, String layout, LocalDateTime registeredDate) {
		this.juryId = juryId;
		this.area = area;
		this.ward = ward;
		this.layout = layout;
		this.registeredDate = registeredDate;
	}

	public Jurisdiction(String area, String ward, String layout) {
		this.area = area;
		this.ward = ward;
		this.layout = layout;
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

	public List<Officer> getOfficers() {
		return officers;
	}

	public void setOfficers(List<Officer> officers) {
		this.officers = officers;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public LocalDateTime getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDateTime localDateTime) {
		this.registeredDate = localDateTime;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Override
	public String toString() {
		return "Jurisdiction [juryId=" + juryId + ", area=" + area + ", ward=" + ward + ", layout=" + layout
				+ ", officers=" + officers + "]";
	}
}
