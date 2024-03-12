package com.team3.wecare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.EmailModel;
import com.team3.wecare.models.UserModel;
import com.team3.wecare.service.ComplaintService;
import com.team3.wecare.service.EmailSenderService;
import com.team3.wecare.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	private static final String LANDING_PAGE_VIEW = "weCareHome";
	private static final String LOGIN_PAGE_VIEW = "login";
	private static final String REGISTRATION_PAGE_VIEW = "registration";

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private ComplaintService complaintService;

	@GetMapping("/wecare/registration")
	public String showRegistrationForm() {
		return REGISTRATION_PAGE_VIEW;
	}

	@PostMapping("/wecare/registration")
	public String handleRegistration(@Valid @ModelAttribute("user") UserModel userModel, Model model,
			BindingResult bindingResult) {
		try {
			userService.createUser(userModel);
			model.addAttribute("sMessage", "You are registered Successfully !");

			return REGISTRATION_PAGE_VIEW;
		} catch (WeCareException e) {
			model.addAttribute("eMessage", e.getMessage());

			return REGISTRATION_PAGE_VIEW;
		}
	}

	@PostMapping("/wecare/generateEmailOtp")
	public ResponseEntity<String> genetrateEmailOtp(@RequestBody String email) {
		log.info("Generate otp method is called");
		log.info("Email : {}", email);
		String otp = emailSenderService.getotp(email);
		log.info("Generated otp is : {}", otp);

		return new ResponseEntity<>("Otp Sent Successfully", HttpStatus.OK);
	}

	@PostMapping("/wecare/validateOtp")
	public ResponseEntity<String> validate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EmailModel validateOtpDto) {
		String email = validateOtpDto.getEmail();
		String otp = validateOtpDto.getOtp();
		log.info("validate otp method is called");

		try {
			emailSenderService.validate(email, otp);
		} catch (WeCareException e) {
			log.error("Otp invalid or expired");

			return new ResponseEntity<>("OTP is invalid or expired.", HttpStatus.GATEWAY_TIMEOUT);
		}

		return new ResponseEntity<>("OTP verified succesfully.", HttpStatus.OK);
	}

	@GetMapping("/wecare")
	public String landingPage(Model model) {
		long usersCount = userService.countUsers();
		long complaintsCount = complaintService.countComplaints();
		long closedComplaintsCount = complaintService.getCountOfClosedComplaints();
		model.addAttribute("usersCount", usersCount);
		model.addAttribute("complaintsCount", complaintsCount);
		model.addAttribute("closedComplaintsCount", closedComplaintsCount);

		return LANDING_PAGE_VIEW;
	}
	
	@GetMapping("/wecare/login")
	public String loginPage() {
		return LOGIN_PAGE_VIEW;
	}
}