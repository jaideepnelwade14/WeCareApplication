package com.team3.wecare.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.Roles;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.OfficerModel;
import com.team3.wecare.repositories.ComplaintRepo;
import com.team3.wecare.repositories.OfficerRepo;
import com.team3.wecare.repositories.RolesRepo;
import com.team3.wecare.service.EmailSenderService;
import com.team3.wecare.service.OfficerService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OfficerServiceImpl implements OfficerService {
	private static final Logger logger = LoggerFactory.getLogger(OfficerServiceImpl.class);
	private static final String OFFICER_ERROR_MESSAGE = "Officer can't be deleted. There are associated grievances.";
	private static final String OFFICER_DOES_NOT_EXIST = "officer does not exist";

	@Autowired
	private OfficerRepo officerRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepo rolesrepo;
	@Autowired
	private ComplaintRepo complaintRepo;
	@Autowired
	private EmailSenderService emailSenderService;
	
	Random rand = new Random();

	@Override
	public Officer updateOfficer(OfficerModel officerModel, Jurisdiction jury){
		logger.debug("Updating Officer details with Id: {}",officerModel.getOfficerId());
		Officer existingOfficer = officerRepo.getReferenceById(officerModel.getOfficerId());
		existingOfficer.setOfficerName(officerModel.getOfficerName());
		existingOfficer.setAddress(officerModel.getAddress());
		existingOfficer.setEmail(officerModel.getEmail());
		existingOfficer.setPhone(officerModel.getPhone());
		existingOfficer.setJuryOfficer(jury);
		existingOfficer.setModifiedDate(LocalDateTime.now());
		
		return officerRepo.save(existingOfficer);
	}

	@Override
	public Officer getOfficer(int officerId) {
		logger.debug("Retrieving Officer with Id {} !",officerId);
		
		return officerRepo.getReferenceById(officerId);
	}

	@Override
	public List<Officer> getAllOfficers() {
		logger.debug("Retrieving all the Officers Created !");
		
		return officerRepo.findAll();
	}

	@Override
	public String deleteOfficer(int officerId) {
		logger.debug("Officers details deleted");
		Optional<Officer> optionalOfficer = officerRepo.findById(officerId);

		if (optionalOfficer.isPresent()) {
			Officer officer = optionalOfficer.get();
			List<Complaint> complaints = complaintRepo.getComplaintByOfficer(officer);
			if (!complaints.isEmpty()) {

				return OFFICER_ERROR_MESSAGE;
			}
			officerRepo.deleteOfficerById(officerId);

			return null;
		}

		return OFFICER_DOES_NOT_EXIST;
	}

	@Override
	public Officer createOfficer(OfficerModel officerCreateModel, Jurisdiction jury) throws Exception, WeCareException{
		logger.debug("Creating an Officer and assigning him to a Jurisdiction ! ");
		Officer officerByEmail = officerRepo.findByEmail(officerCreateModel.getEmail());
		Officer officerByPhone = officerRepo.findOfficerByPhone(officerCreateModel.getPhone());
		
		if(Objects.isNull(officerByPhone) && Objects.isNull(officerByEmail)) {
			Officer officer = new Officer(officerCreateModel.getOfficerName(), officerCreateModel.getAddress(),
					officerCreateModel.getEmail(), officerCreateModel.getPhone());
			officer.setJuryOfficer(jury);
			officer.setCreatedDate(LocalDateTime.now());
			Roles role = rolesrepo.findRolesByroleName("OFFICER");
			officer.setRole(role);
			String firstThreeLetters = officerCreateModel.getOfficerName().substring(0, 3);
			String result = firstThreeLetters.substring(0, 1).toUpperCase() + firstThreeLetters.substring(1);
			String lastFourDigits = officer.getPhone().substring(officer.getPhone().length() - 4);
			String finalPassword = result + "@" + lastFourDigits;
			String password = passwordEncoder.encode(finalPassword);
			officer.setPassword(password);
			officerRepo.save(officer);
			emailSenderService.sendEmailToOfficer(officer,jury);
			logger.info("Creating an Officer and assigning him to a Jurisdiction ! ");
			return officerRepo.save(officer);
		}
		else {
			logger.error("Officer with Email Id or Phone No. already Exists !");
			throw new WeCareException("Officer with Email Id or Phone No. already Exists !");
		}
	}

	@Override
	public Officer findOfficerByEmail(String email) {
		logger.debug("Retrieving Officer by Email: {}",email);

		return officerRepo.findByEmail(email);
	}

	@Override
	public Officer findOfficerByPhoneNumber(String phone) {
		logger.debug("Retrieving Officer by Phone No.: {}",phone);

		return officerRepo.findOfficerByPhone(phone);
	}

	@Override
	public Long countOfficers() {
		logger.debug("Counting total number of Officers Created !");

		return officerRepo.count();
	}

	@Override
	public Boolean isCurrentPasswordValid(String email, String currentPassword) {
		logger.debug("Checking if {}'s current Password is Valid !",email);
		Officer officer = officerRepo.findByEmail(email);

		return officer != null && passwordEncoder.matches(currentPassword, officer.getPassword());
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		logger.debug("Updating Password for {} !",email);
		Officer officer = officerRepo.findByEmail(email);

		if (officer != null) {
			officer.setPassword(passwordEncoder.encode(newPassword));
			officerRepo.save(officer);
		}
	}

	@Override
	public Officer getOfficerByDetails(String email, String phone, int officerId) {
		return officerRepo.getOfficerByDetails(email, phone, officerId);
	}

	@Override
	public Officer updateOfficerProfile(OfficerModel officerModel) {
		logger.info("Updating Officer Details");
		Officer officer = officerRepo.getReferenceById(officerModel.getOfficerId());
		officer.setOfficerName(officerModel.getOfficerName());
		officer.setAddress(officerModel.getAddress());
		officer.setPhone(officerModel.getPhone());
		officer.setEmail(officerModel.getEmail());
		officer.setModifiedDate(LocalDateTime.now());
		logger.debug("Updating Officer Details");
		
		return officerRepo.save(officer);
	}
}
