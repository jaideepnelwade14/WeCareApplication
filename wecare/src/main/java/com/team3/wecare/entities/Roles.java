package com.team3.wecare.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	@Column(nullable = false, length = 8)
	private String roleName;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
	private List<User> userId;

	@OneToMany(targetEntity = Admin.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
	private List<Admin> adminId;

	@OneToMany(targetEntity = Officer.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "role")
	private List<Officer> officerId;

	public Roles() {
	}

	public Roles(int roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Roles(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUserId() {
		return userId;
	}

	public void setUserId(List<User> userId) {
		this.userId = userId;
	}

	public List<Admin> getAdminId() {
		return adminId;
	}

	public void setAdminId(List<Admin> adminId) {
		this.adminId = adminId;
	}

	public List<Officer> getOfficerId() {
		return officerId;
	}

	public void setOfficerId(List<Officer> officerId) {
		this.officerId = officerId;
	}

	@Override
	public String toString() {
		return "Roles [roleName=" + roleName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Roles other = (Roles) obj;
		return Objects.equals(roleName, other.roleName);
	}
}