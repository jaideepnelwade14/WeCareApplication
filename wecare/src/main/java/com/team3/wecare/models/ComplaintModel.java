package com.team3.wecare.models;

import java.time.LocalDateTime;

import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Status;

public class ComplaintModel {

	private int complId;
	private String area;
	private String ward;
	private String layout;
	private String address;
	private String landmark;
	private String issue;
	private String comment;
	private LocalDateTime createdDate;
	private String response;
	private Status status;

	public ComplaintModel() {

	}

	public ComplaintModel(String address, String landmark, String comment, String issue) {
		this.address = address;
		this.landmark = landmark;
		this.comment = comment;
		this.issue = issue;
	}

	public ComplaintModel(LocalDateTime createdDate, String response, Status status) {
		this.createdDate = createdDate;
		this.response = response;
		this.status = status;
	}

	public ComplaintModel(Complaint complaint) {
		this.complId = complaint.getComplId();
		this.createdDate = complaint.getCreatedDate();
		this.response = complaint.getResponse();
		this.status = complaint.getStatus();

		if (complaint.getJuryComplaint() != null) {
			this.area = complaint.getJuryComplaint().getArea();
			this.ward = complaint.getJuryComplaint().getWard();
			this.layout = complaint.getJuryComplaint().getLayout();
		}
	}

	public int getComplId() {
		return complId;
	}

	public void setComplId(int complId) {
		this.complId = complId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}