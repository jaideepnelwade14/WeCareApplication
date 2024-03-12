package com.team3.wecare.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Officer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int officerId;
	@Column(nullable = false, length = 40)
	private String officerName;
	@Column(nullable = false, length = 255)
	private String address;
	@Column(nullable = false, length = 60)
	private String email;
	@Column(nullable = false, length = 10)
	private String phone;
	@Column(nullable = false, length = 12)
	private String password;
	@Column(nullable = false)
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	@ManyToOne(targetEntity = Jurisdiction.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "juryId", referencedColumnName = "juryId")
	private Jurisdiction juryOfficer;

	@OneToMany(targetEntity = Complaint.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "officer")
	@Column(nullable = false)
	private List<Complaint> complaints;

	@ManyToOne(targetEntity = Roles.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
	private Roles role;

	public Officer() {
	}

	public Officer(String officerName, String address, String email, String phone, Jurisdiction juryOfficer) {
		this.officerName = officerName;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.juryOfficer = juryOfficer;
	}

	public Officer(String officerName, String address, String email, String phone, String password,
			LocalDateTime createdDate, LocalDateTime modifiedDate) {
		this.officerName = officerName;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public Officer(String officerName, String address, String email, String phone, String password) {
		this.officerName = officerName;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public Officer(String officerName, String address, String email, String phone) {
		this.officerName = officerName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public Officer(int officerId, String officerName, String address, String email, String phone) {
		this.officerId = officerId;
		this.officerName = officerName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public int getOfficerId() {
		return officerId;
	}

	public void setOfficerId(int officerId) {
		this.officerId = officerId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime localDateTime) {
		this.createdDate = localDateTime;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Jurisdiction getJuryOfficer() {
		return juryOfficer;
	}

	public void setJuryOfficer(Jurisdiction juryOfficer) {
		this.juryOfficer = juryOfficer;
	}

	public List<Complaint> getComplaint() {
		return complaints;
	}

	public void setComplaint(List<Complaint> complaint) {
		this.complaints = complaint;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Roles getRole() {
		return role;
	}

	public boolean hasComplaints() {
		return complaints != null && !complaints.isEmpty();
	}

	@Override
	public String toString() {
		return "Officer [officerId=" + officerId + ", officerName=" + officerName + ", Address=" + address + ", email="
				+ email + ", phone=" + phone + ", password=" + password + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", jury=" + juryOfficer + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Officer other = (Officer) obj;
		return Objects.equals(email, other.email) && Objects.equals(phone, other.phone);
	}
}