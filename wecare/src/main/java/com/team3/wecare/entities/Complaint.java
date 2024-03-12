package com.team3.wecare.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int complId;
	@Column(nullable = false, length = 255)
	private String address;
	@Column(nullable = false, length = 50)
	private String landmark;
	@Column(length = 1000)
	private String response;
	@Column(nullable = false, length = 30)
	private String issue;
	@Column(nullable = false, length = 2000)
	private String comments;
	@Column(length = 11)
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(nullable = false)
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	@ManyToOne(targetEntity = User.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
	private User user;

	@ManyToOne(targetEntity = Jurisdiction.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "juryId", referencedColumnName = "juryId", nullable = false)
	private Jurisdiction juryComplaint;

	@ManyToOne(targetEntity = Officer.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "officerId", referencedColumnName = "officerId")
	private Officer officer;

	public Complaint() {
	}

	public Complaint(String address, String landmark, String issue, String comments) {
		this.address = address;
		this.landmark = landmark;
		this.issue = issue;
		this.comments = comments;
	}

	public int getComplId() {
		return complId;
	}

	public void setComplId(int complId) {
		this.complId = complId;
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

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Jurisdiction getJuryComplaint() {
		return juryComplaint;
	}

	public void setJuryComplaint(Jurisdiction juryComplaint) {
		this.juryComplaint = juryComplaint;
	}

	public Officer getOfficer() {
		return officer;
	}

	public void setOfficer(Officer officer) {
		this.officer = officer;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
}