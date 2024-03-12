package com.team3.wecare.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team3.wecare.customlogindetails.CustomAdminDetails;
import com.team3.wecare.customlogindetails.CustomOfficerDetails;
import com.team3.wecare.customlogindetails.CustomUserDetails;
import com.team3.wecare.entities.Admin;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.User;
import com.team3.wecare.repositories.AdminRepo;
import com.team3.wecare.repositories.OfficerRepo;
import com.team3.wecare.repositories.UserRepo;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private OfficerRepo officerRepo;
	@Autowired
	private AdminRepo adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		Officer officer = officerRepo.findByEmail(username);
		Admin admin = adminRepo.findByEmail(username);

		if (user != null) {
			return new CustomUserDetails(user);
		} else if (officer != null) {
			return new CustomOfficerDetails(officer);
		} else if (admin != null) {
			return new CustomAdminDetails(admin);
		} else {
			throw new UsernameNotFoundException("user not found");
		}
	}
}