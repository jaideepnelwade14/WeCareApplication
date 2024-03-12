package com.team3.wecare.customlogindetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.team3.wecare.entities.Officer;

public class CustomOfficerDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Officer officer;

	public CustomOfficerDetails() {
	}

	public CustomOfficerDetails(Officer officer) {
		this.officer = officer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> officer.getRole().getRoleName());
	}

	@Override
	public String getPassword() {
		return officer.getPassword();
	}

	@Override
	public String getUsername() {
		return officer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}