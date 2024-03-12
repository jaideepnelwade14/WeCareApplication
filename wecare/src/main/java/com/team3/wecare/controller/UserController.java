package com.team3.wecare.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.User;
import com.team3.wecare.models.ComplaintModel;
import com.team3.wecare.models.PasswordModel;
import com.team3.wecare.models.UserModel;
import com.team3.wecare.service.ComplaintService;
import com.team3.wecare.service.JurisdictionService;
import com.team3.wecare.service.UserService;

@Controller
@RequestMapping("/wecare/user")
public class UserController {
	private static final String REDIRECT_USER_HOME_PAGE_VIEW = "redirect:/wecare/user/track-complaints";
	private static final String USER_HOME_PAGE_VIEW = "userHomePage";
	private static final String USER_VIEW_PROFILE_PAGE_VIEW = "viewUserProfile";
	private static final String USER_CREATE_COMPLAINT_PAGE_VIEW = "createComplaint";
	private static final String USER_CHANGE_PASSWORD_PAGE_VIEW = "changeUserPassword";
	private static final String USER_EDIT_PROFILE_PAGE_VIEW = "editUserProfile";
	private static final String REDIRECT_LOGIN_LOGOUT = "redirect:/wecare/login?logout";
	private static final String USER_VIEW_COMPLAINT_DETAILS = "userTrackComplaint";
	private static final String REDIRECT_USER_EDIT_PAGE_VIEW_WITH_ID = "redirect:/wecare/user/edit-profile?id=";
  private static final String SUCCESS_MESSAGE = "successMessage";
	private static final String REDIRECT_USER_VIEW_PROFILE_PAGE_VIEW = "redirect:/wecare/user/view-profile";

	@Autowired
	private UserService userService;
	@Autowired
	private JurisdictionService juryService;
	@Autowired
	private ComplaintService complaintService;

	private void userDetails(Model model, UserDetails userDetails) {
		User user = userService.findUserByEmail(userDetails.getUsername());
		String name = user.getFirstName();
		model.addAttribute("userName", name);
	}

	@GetMapping("/track-complaints")
	public String userHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute("successMessage") String successMessage) {
		userDetails(model, userDetails);
		User user = userService.findUserByEmail(userDetails.getUsername());
		List<Complaint> userComplaints = complaintService.getComplaintsByUser(user);

		if (!userComplaints.isEmpty()) {
			model.addAttribute("complaints", userComplaints);
			model.addAttribute(SUCCESS_MESSAGE, successMessage);
		} else {
			model.addAttribute("noComplaintsMessage", "No Complaints Created Yet.");
		}

		return USER_HOME_PAGE_VIEW;
	}

	@GetMapping("/create-complaint")
	public String createComplaintPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		userDetails(model, userDetails);
		List<String> juryDetails = juryService.getDistinctArea();
		model.addAttribute("jurisdictions", juryDetails);
		model.addAttribute("complaint", new ComplaintModel());

		return USER_CREATE_COMPLAINT_PAGE_VIEW;
	}

	@GetMapping("/wards")
	@ResponseBody
	public List<String> getWardsByArea(@RequestParam String area) {
		return juryService.getWardsByArea(area);
	}

	@GetMapping("/layouts")
	@ResponseBody
	public List<String> getLayoutsByWard(@RequestParam String ward) {
		return juryService.getLayoutByWard(ward);
	}

	@PostMapping("/create-complaint")
	public String createComplaint(@ModelAttribute("complaint") ComplaintModel complaint, Model model,
			@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
		Jurisdiction jury = juryService.getJuryByDetails(complaint.getArea(), complaint.getWard(),
				complaint.getLayout());
		User user = userService.findUserByEmail(userDetails.getUsername());
		try {
			complaintService.createComplaint(complaint, jury, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Complaint Created Successfully!");

		return REDIRECT_USER_HOME_PAGE_VIEW;
	}

	@GetMapping("/view-profile")
	public String userProfilePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		userDetails(model, userDetails);
		User user = userService.findUserByEmail(userDetails.getUsername());
		List<Complaint> complaints = complaintService.getComplaintsByUser(user);
		model.addAttribute("userDetails", user);
		model.addAttribute("complaintsCount", complaints.size());

		return USER_VIEW_PROFILE_PAGE_VIEW;
	}

	@GetMapping("/change-password")
	public String showchangePassword(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		userDetails(model, userDetails);
		model.addAttribute("passwordModel", new PasswordModel());

		return USER_CHANGE_PASSWORD_PAGE_VIEW;
	}

	@PostMapping("/change-password")
	public String changePassword(@ModelAttribute PasswordModel passwordModel, Model model,
			@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
		String username = userDetails.getUsername();
		Boolean isPasswordValid = userService.isCurrentPasswordValid(username, passwordModel.getCurrentPassword());

		if (Boolean.FALSE.equals(isPasswordValid)) {
		    model.addAttribute("passwordError", "Current password is incorrect.");
		    return USER_CHANGE_PASSWORD_PAGE_VIEW;
		}
		userService.updatePassword(username, passwordModel.getNewPassword());
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Password Changed successfully !");

		return REDIRECT_LOGIN_LOGOUT;
	}

	@GetMapping("/edit-profile")
	public String editUserProfilePage(@RequestParam Integer id, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		userDetails(model, userDetails);
		User user = userService.getUser(id);
		model.addAttribute("id", id);
		model.addAttribute("user", user);

		return USER_EDIT_PROFILE_PAGE_VIEW;
	}

	@PostMapping("/update-profile")
	public String updateUserProfile(Model model, @ModelAttribute UserModel userModel,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
		userDetails(model, userDetails);
		User user = userService.getUserByDetails(userModel.getEmail(), userModel.getPhone(), userModel.getUserId());

		if (Objects.nonNull(user)) {
			model.addAttribute("newUser", userModel);
			model.addAttribute("user", userService.getAllUsers());
			redirectAttributes.addFlashAttribute("errorMessage", "User with email or phone already exists !");

			return REDIRECT_USER_EDIT_PAGE_VIEW_WITH_ID + userModel.getUserId();
		}
		if (!userDetails.getUsername().equals(userModel.getEmail())) {
			userService.updateUserProfile(userModel);
			redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Profile Updated Successfully. Please Login again !");
			
			return REDIRECT_LOGIN_LOGOUT;
		}
		userService.updateUserProfile(userModel);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Profile updated successfully !");
		
		return REDIRECT_USER_VIEW_PROFILE_PAGE_VIEW;
	}

	@GetMapping("/track-complaints/view-details")
	public String showComplaintDetails(@RequestParam("complaintId") int complaintId, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		userDetails(model, userDetails);
		Complaint complaint = complaintService.getComplaint(complaintId);
		model.addAttribute("complaint", complaint);

		return USER_VIEW_COMPLAINT_DETAILS;
	}
}