package com.team3.wecare.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(nullable = false, length = 15)
	private String userName;
	@Column(nullable = false, length = 15)
	private String firstName;
	@Column(length = 15)
	private String lastName;
	@Column(nullable = false, length = 60)
	private String email;
	@Column(nullable = false, length = 10)
	private String phone;
	@Column(nullable = false)
	private boolean verified;
	@Column(nullable = false, length = 12)
	private String password;
	@CreationTimestamp
	@Column(name = "registeredDate", nullable = false)
	private LocalDateTime date;
	private LocalDateTime modifiedDate;

	@OneToMany(targetEntity = Complaint.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "user")
	private List<Complaint> complaints;

	@ManyToOne(targetEntity = Roles.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
	private Roles role;

	public User() {
	}

	public User(String userName, String firstName, String lastName, String email, String phone, boolean verified,
			LocalDateTime modifiedDate) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.verified = verified;
		this.modifiedDate = modifiedDate;
	}

	public User(String userName, String firstName, String lastName, String email, String phone, String password) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getRegisteredDate() {
		return date;
	}

	public void setRegisteredDate(LocalDateTime localDateTime) {
		this.date = localDateTime;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<Complaint> getComplaint() {
		return complaints;
	}

	public void setComplaint(List<Complaint> complaint) {
		this.complaints = complaint;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone + ", verified=" + verified + ", password="
				+ password + ", registeredDate=" + date + ", modifiedDate=" + modifiedDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}
}