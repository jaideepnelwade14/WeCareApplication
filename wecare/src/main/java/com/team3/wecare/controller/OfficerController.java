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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.User;
import com.team3.wecare.models.OfficerModel;
import com.team3.wecare.models.PasswordModel;
import com.team3.wecare.service.ComplaintService;
import com.team3.wecare.service.OfficerService;

@Controller
@RequestMapping("/wecare/officer")
public class OfficerController {
	private static final String OFFICER_HOME_PAGE_VIEW = "officerHomePage";
	private static final String OFFICER_CHANGE_PASSWORD_PAGE_VIEW = "changeOfficerPassword";
	private static final String OFFICER_VIEW_PROFILE_PAGE_VIEW = "viewOfficerProfile";
	private static final String OFFICER_EDIT_PROFILE_PAGE_VIEW = "editOfficerProfile";
	private static final String REDIRECT_LOGIN_LOGOUT = "redirect:/wecare/login?logout";
	private static final String OFFICER_VIEW_COMPLAINT_DETAILS = "officerTrackComplaint";
	private static final String REDIRECT_OFFICER_EDIT_PAGE_VIEW_WITH_ID = "redirect:/wecare/officer/edit-profile?id=";
	private static final String REDIRECT_OFFICER_VIEW_PROFILE_PAGE_VIEW = "redirect:/wecare/officer/view-profile";
	private static final String REDIRECT_OFFICER_VIEW_COMPLAINT_DETAILS = "redirect:/wecare/officer/track-complaints/view-details?complaintId=";
	private static final String SUCCESS_MESSAGE = "successMessage";
	
	@Autowired
	private OfficerService officerService;
	@Autowired
	private ComplaintService complaintService;

	private void officerDetails(Model model, UserDetails userDetails) {
		Officer officer = officerService.findOfficerByEmail(userDetails.getUsername());
		String name = officer.getOfficerName();
		model.addAttribute("officerName", name);
	}

	@GetMapping("track-complaints")
	public String officerHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		officerDetails(model, userDetails);
		Officer officer = officerService.findOfficerByEmail(userDetails.getUsername());
		List<Complaint> complDetails = complaintService.getComplaintByOfficer(officer);

		if (!complDetails.isEmpty()) {
			model.addAttribute("complaints", complDetails);
		} else {
			model.addAttribute("noComplaintsMessage", "No Complaints Assigned Yet.");
		}

		return OFFICER_HOME_PAGE_VIEW;
	}

	@GetMapping("/view-profile")
	public String viewOfficerProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		officerDetails(model, userDetails);
		Officer officer = officerService.findOfficerByEmail(userDetails.getUsername());
		List<Complaint> complaints = complaintService.getComplaintByOfficer(officer);
		Jurisdiction jurisdiction = officer.getJuryOfficer();
		model.addAttribute("juryDetails", jurisdiction);
		model.addAttribute("officerDetails", officer);
		model.addAttribute("complaintsCount", complaints.size());

		return OFFICER_VIEW_PROFILE_PAGE_VIEW;
	}

	@GetMapping("/change-password")
	public String changeOfficerPassword(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		officerDetails(model, userDetails);
		model.addAttribute("passwordModel", new PasswordModel());

		return OFFICER_CHANGE_PASSWORD_PAGE_VIEW;
	}

	@PostMapping("/change-password")
	public String changePassword(@ModelAttribute PasswordModel passwordModel, Model model,
			@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
		officerDetails(model, userDetails);
		String username = userDetails.getUsername();
		Boolean isPasswordValid = officerService.isCurrentPasswordValid(username, passwordModel.getCurrentPassword());

		if (Boolean.FALSE.equals(isPasswordValid)) {
		    model.addAttribute("passwordError", "Current password is incorrect.");
		    return OFFICER_CHANGE_PASSWORD_PAGE_VIEW;
		}
		officerService.updatePassword(username, passwordModel.getNewPassword());
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Password Changed successfully !");

		return REDIRECT_LOGIN_LOGOUT;
	}

	@GetMapping("/edit-profile")
	public String editOfficerProfile(@RequestParam Integer id, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		officerDetails(model, userDetails);
		Officer officer = officerService.getOfficer(id);
		model.addAttribute("id", id);
		model.addAttribute("officer", officer);

		return OFFICER_EDIT_PROFILE_PAGE_VIEW;
	}

	@PostMapping("/update-profile")
	public String updateUserProfile(Model model, @ModelAttribute OfficerModel officerModel,
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
		officerDetails(model, userDetails);
		Officer officer = officerService.getOfficerByDetails(officerModel.getEmail(), officerModel.getPhone(), officerModel.getOfficerId());

		if (Objects.nonNull(officer)) {
			model.addAttribute("newOfficer", officerModel);
			model.addAttribute("officer", officerService.getAllOfficers());
			redirectAttributes.addFlashAttribute("errorMessage", "Officer with email or phone already exists !");

			return REDIRECT_OFFICER_EDIT_PAGE_VIEW_WITH_ID + officerModel.getOfficerId();
		}
		if (!userDetails.getUsername().equals(officerModel.getEmail())) {
			officerService.updateOfficerProfile(officerModel);
			redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Profile updated successfully.Please log in again!");
			
			return REDIRECT_LOGIN_LOGOUT;
		}
		
		officerService.updateOfficerProfile(officerModel);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Profile updated successfully !");

		return REDIRECT_OFFICER_VIEW_PROFILE_PAGE_VIEW;
	}

	@GetMapping("/track-complaints/view-details")
	public String showComplaintDetais(@RequestParam("complaintId") int complaintId, Model model,
			@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute("successMessage") String successMessage) {
		officerDetails(model, userDetails);
		Complaint complaint = complaintService.getComplaint(complaintId);
		model.addAttribute("complaint", complaint);
		model.addAttribute(SUCCESS_MESSAGE, successMessage);

		return OFFICER_VIEW_COMPLAINT_DETAILS;
	}

	@GetMapping("/wecare/officer/track-complaints/view-details/save-response/{complaintId}")
	public String showResponseForm(@PathVariable int complaintId, Model model) {
		Complaint complaint = complaintService.getComplaint(complaintId);
		model.addAttribute("complaint", complaint);

		return REDIRECT_OFFICER_VIEW_COMPLAINT_DETAILS + complaintId;
	}

	@PostMapping("/track-complaints/view-details/save-response/{complaintId}")
	public String saveResponse(@RequestParam("complaintId") int complaintId, @RequestParam("response") String response,User user,
                            RedirectAttributes redirectAttributes) throws Exception {
		Complaint complaint = complaintService.getComplaint(complaintId);

		if (complaint != null) {
			complaint.setResponse(response);
			complaintService.saveComplaint(complaint,user);
      redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Response for the Complaint has been Submitted !");
		}

		return REDIRECT_OFFICER_VIEW_COMPLAINT_DETAILS + complaintId;
	}
}