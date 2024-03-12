package com.team3.wecare.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.team3.wecare.entities.Roles;
import com.team3.wecare.entities.User;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.UserModel;
import com.team3.wecare.repositories.RolesRepo;
import com.team3.wecare.repositories.UserRepo;
import com.team3.wecare.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepo rolesRepo;

	@Override
	public User updateUserProfile(UserModel userModel) {
		logger.debug("Updating User details with Id: {}",userModel.getUserId());

		User user=userRepo.getReferenceById(userModel.getUserId());
		user.setUserName(userModel.getUserName());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setPhone(userModel.getPhone());
		user.setEmail(userModel.getEmail());
		user.setModifiedDate(LocalDateTime.now());
		
		return userRepo.save(user);
	}

	@Override
	public User getUser(int userId) {
		logger.debug("Retrieving User with Id {} !",userId);
		
		return userRepo.getReferenceById(userId);
	}

	@Override
	public User findUserByEmail(String email) {
		logger.debug("Retrieving User by Email: {}",email);
		
		return userRepo.findByEmail(email);
	}

	@Override
	public Boolean isCurrentPasswordValid(String email, String currentPassword) {
		logger.debug("Checking if {}'s current Password is Valid !",email);
		User user = userRepo.findByEmail(email);

		return user != null && passwordEncoder.matches(currentPassword, user.getPassword());
	}

	@Override
	public Long countUsers() {
		logger.debug("Counting total number of Users Registered !");

		return userRepo.count();
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		logger.debug ("Updating Password for {} !",email);
		User user = userRepo.findByEmail(email);

		if (user != null) {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepo.save(user);
		}
	}

	@Override
	public User createUser(UserModel userModel) throws WeCareException {
		logger.debug("Registering a new User !");
		User existingUser = userRepo.findByEmail(userModel.getEmail());
		if (existingUser == null) {
			User user = new User(userModel.getUserName(), userModel.getFirstName(), userModel.getLastName(),
					userModel.getEmail(), userModel.getPhone(), userModel.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			Roles role = rolesRepo.findRolesByroleName("USER");
			user.setRole(role);
			user.setRegisteredDate(LocalDateTime.now());
			logger.info("Registration of the new User Successfull !");
			return userRepo.save(user);
		} else {
			logger.error("User with EmailId already Exists !:" );
			throw new WeCareException("User with EmailId already Exists !: ");
		}
	}

	@Override
	public User getUserByDetails(String email, String phone, int userId) {
		return userRepo.getUserByDetails(email, phone, userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
}
