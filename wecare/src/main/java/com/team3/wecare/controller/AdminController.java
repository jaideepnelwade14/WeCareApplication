package com.team3.wecare.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team3.wecare.entities.Admin;
import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.Status;
import com.team3.wecare.entities.User;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.AdminModel;
import com.team3.wecare.models.JurisdictionModel;
import com.team3.wecare.models.OfficerModel;
import com.team3.wecare.models.PasswordModel;
import com.team3.wecare.service.AdminService;
import com.team3.wecare.service.ComplaintService;
import com.team3.wecare.service.JurisdictionService;
import com.team3.wecare.service.OfficerService;
import com.team3.wecare.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/wecare/admin")
public class AdminController {
	private static final String ADMIN_TRACK_COMPLAINTS_PAGE_VIEW = "adminHomePage";
	private static final String ADMIN_JURISDICTION_PAGE_VIEW = "adminJurisdiction";
	private static final String ADMIN_OFFICER_PAGE_VIEW = "adminOfficer";
	private static final String REDIRECT_ADMIN_JURISDICTION_PAGE_VIEW = "redirect:/wecare/admin/jurisdictions";
	private static final String REDIRECT_ADMIN_OFFICER_PAGE_VIEW = "redirect:/wecare/admin/officers";
	private static final String ADMIN_EDIT_JURISDICTION_PAGE_VIEW = "editJurisdiction";
	private static final String ADMIN_ADD_JURISDICTION_PAGE_VIEW = "addJurisdiction";
	private static final String ADMIN_EDIT_OFFICER_PAGE_VIEW = "editOfficer";
	private static final String ADMIN_ADD_OFFICER_PAGE_VIEW = "addOfficer";
	private static final String ADMIN_ADD_ADMIN_PAGE_VIEW = "addAdmin";
	private static final String ADMIN_CHANGE_PASSWORD_PAGE_VIEW = "changeAdminPassword";
	private static final String ADMIN_VIEW_PROFILE_PAGE_VIEW = "viewAdminProfile";
	private static final String REDIRECT_ADMIN_EDIT_OFFICER_PAGE_VIEW_WITH_ID = "redirect:/wecare/admin/officers/edit-officer?id=";
	private static final String REDIRECT_ADMIN_EDIT_JURY_PAGE_VIEW_WITH_ID = "redirect:/wecare/admin/jurisdictions/edit-jury?id=";
	private static final String REDIRECT_LOGIN_LOGOUT = "redirect:/wecare/login?logout";
	private static final String REDIRECT_ADMIN_VIEW_PROFILE_PAGE_VIEW = "redirect:/wecare/admin/view-profile";
	private static final String ADMIN_VIEW_COMPLAINT_DETAILS = "adminTrackComplaint";
	private static final String SUCCESS_MESSAGE = "successMessage";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String NEW_JURISDICTION = "newJurisdiction";
	private static final String MESSAGE = "message";
	private static final String JURISDICTION = "jurisdictions";

	@Autowired
	private OfficerService officerService;
	@Autowired
	private JurisdictionService juryService;
	@Autowired
	private ComplaintService complService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;

	private void adminDetails(Model model, UserDetails userDetails) {
		Admin admin = adminService.findAdminByEmail(userDetails.getUsername());
		String name = admin.getAdminName();
		model.addAttribute("adminName", name);
	}

	private void countOfEntries(Model model) {
		long usersCount = userService.countUsers();
		long officersCount = officerService.countOfficers();
		long juryCount = juryService.countJurisdictions();
		long complaintsCount = complService.countComplaints();

		model.addAttribute("usersCount", usersCount);
		model.addAttribute("officersCount", officersCount);
		model.addAttribute("juryCount", juryCount);
		model.addAttribute("complaintsCount", complaintsCount);
	}

	@GetMapping("track-complaints")
	public String adminHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("successMessage") String successMessage) {
		adminDetails(model, userDetails);
		List<Complaint> complDetails = complService.getAllComplaints();

		if (!complDetails.isEmpty()) {
			model.addAttribute("complaints", complDetails);
		} else {
			model.addAttribute("noComplaintsMessage", "No Complaints Registered Yet.");
		}
		model.addAttribute(SUCCESS_MESSAGE, successMessage);
		model.addAttribute("allStatuses",Status.values());
		return ADMIN_TRACK_COMPLAINTS_PAGE_VIEW;
	}

	@GetMapping("/jurisdictions")
	public String adminJurisdiction(
	      Model model, @ModelAttribute("successMessage") String successMessage, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("errorMessage") String errorMessage
	) {
		adminDetails(model, userDetails);
		List<Jurisdiction> juryDetails = juryService.getAllJurisdictions();

		if (!juryDetails.isEmpty()) {
			model.addAttribute(JURISDICTION, juryDetails);
			model.addAttribute(SUCCESS_MESSAGE, successMessage);
			model.addAttribute(ERROR_MESSAGE, errorMessage);
		} else {
			model.addAttribute("noJurisdictionsMessage", "No Jurisdictions Added Yet.");
		}

		return ADMIN_JURISDICTION_PAGE_VIEW;
	}

	@GetMapping("/jurisdictions/delete-jury")
	public String deleteJurisdiction(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		String errorMessage = juryService.deleteJurisdictionById(id);

		if (errorMessage == null) {
			redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Jurisdiction deleted successfully!");
		} else {
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
		}

		return REDIRECT_ADMIN_JURISDICTION_PAGE_VIEW;
	}

	@GetMapping("/jurisdictions/edit-jury")
	public String editJurisdiction(@RequestParam Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		Jurisdiction jurisdiction = juryService.getJurisdiction(id);
		model.addAttribute("id", id);
		model.addAttribute("jury", jurisdiction);

		return ADMIN_EDIT_JURISDICTION_PAGE_VIEW;
	}

	@PostMapping("/jurisdictions/update-jury")
	public String updateJurisdiction(Model model, @ModelAttribute JurisdictionModel juryModel, RedirectAttributes redirectAttributes) {
		Jurisdiction jury = juryService.getJuryByDetails(juryModel.getArea(), juryModel.getWard(), juryModel.getLayout());

		if (Objects.nonNull(jury)) {
			model.addAttribute(NEW_JURISDICTION, juryModel);
			model.addAttribute(JURISDICTION, juryService.getAllJurisdictions());
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Jurisdiction already exists !");

			return REDIRECT_ADMIN_EDIT_JURY_PAGE_VIEW_WITH_ID + juryModel.getJuryId();
		}
		juryService.updateJurisdiction(juryModel);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Jurisdiction updated successfully !");

		return REDIRECT_ADMIN_JURISDICTION_PAGE_VIEW;
	}

	@GetMapping("/jurisdictions/add")
	public String showAddJurisdictionForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		model.addAttribute(NEW_JURISDICTION, new JurisdictionModel());

		return ADMIN_ADD_JURISDICTION_PAGE_VIEW;
	}

	@PostMapping("/jurisdictions/add")
	public String addJurisdiction(
	      @Valid @ModelAttribute JurisdictionModel juryModel, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails
	) {
		adminDetails(model, userDetails);
		Jurisdiction jury = juryService.getJuryByDetails(juryModel.getArea(), juryModel.getWard(), juryModel.getLayout());

		if (Objects.nonNull(jury)) {
			model.addAttribute(MESSAGE, "Jurisdiction  already exists !");
			model.addAttribute(NEW_JURISDICTION, juryModel);
			model.addAttribute(JURISDICTION, juryService.getAllJurisdictions());
			return ADMIN_ADD_JURISDICTION_PAGE_VIEW;
		}
		juryService.createJurisdiction(juryModel);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Jurisdiction added successfully !");

		return REDIRECT_ADMIN_JURISDICTION_PAGE_VIEW;
	}

	@GetMapping("/officers")
	public String adminOfficer(Model model, @ModelAttribute("successMessage") String successMessage, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		List<OfficerModel> officerDetails = officerService.getAllOfficers().stream().map(OfficerModel::new).toList();
		
		if (!officerDetails.isEmpty()) {
			model.addAttribute("officers", officerDetails);
			model.addAttribute(SUCCESS_MESSAGE, successMessage);
		} else {
			model.addAttribute("noOfficersMessage", "No Officers Created Yet.");
		}

		return ADMIN_OFFICER_PAGE_VIEW;
	}

	@GetMapping("/officers/delete-officer")
	public String deleteOfficer(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes) {
		String errorMessage = officerService.deleteOfficer(id);

		if (errorMessage == null) {
			redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Officer deleted successfully!");
		} else {
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE, errorMessage);
		}

		return REDIRECT_ADMIN_OFFICER_PAGE_VIEW;
	}

	@GetMapping("/officers/edit-officer")
	public String editOfficer(@RequestParam Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		Officer officer = officerService.getOfficer(id);
		model.addAttribute("id", id);
		model.addAttribute("officer", officer);
		List<Jurisdiction> jurisdictions = juryService.getAllJurisdictions();
		model.addAttribute(JURISDICTION, jurisdictions);

		return ADMIN_EDIT_OFFICER_PAGE_VIEW;
	}

	@PostMapping("/officers/update-officer")
	public String updateOfficer(@ModelAttribute OfficerModel officer, @RequestParam Integer juryId, RedirectAttributes redirectAttributes) {
		Officer existingOfficer = officerService.getOfficer(officer.getOfficerId());
		Officer officerByEmail = officerService.findOfficerByEmail(officer.getEmail());
		Officer officerByPhone = officerService.findOfficerByPhoneNumber(officer.getPhone());

		if ((Objects.nonNull(officerByEmail) && officerByEmail.getOfficerId() != (existingOfficer.getOfficerId()))
		      || (Objects.nonNull(officerByPhone) && officerByPhone.getOfficerId() != (existingOfficer.getOfficerId()))) {
			redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Officer with the Email Id or Phone already exists!");

			return REDIRECT_ADMIN_EDIT_OFFICER_PAGE_VIEW_WITH_ID + officer.getOfficerId();
		}
		Jurisdiction updatedJurisdiction = juryService.getJurisdiction(juryId);
		officerService.updateOfficer(officer, updatedJurisdiction);
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Officer updated successfully!");

		return REDIRECT_ADMIN_OFFICER_PAGE_VIEW;
	}

	@GetMapping("/officers/add")
	public String showAddOfficerForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		model.addAttribute("newOfficer", new OfficerModel());
		List<Jurisdiction> jurisdictions = juryService.getAllJurisdictions();
		model.addAttribute(JURISDICTION, jurisdictions);

		return ADMIN_ADD_OFFICER_PAGE_VIEW;
	}

	@PostMapping("/officers/add")
	public String addOfficer(@ModelAttribute OfficerModel newOfficer, Model model, @RequestParam("juryId") Integer juryId, 
			RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		adminDetails(model, userDetails);
		try {
			Jurisdiction jury = juryService.getJurisdiction(juryId);
			officerService.createOfficer(newOfficer, jury);
			redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Officer added successfully !");
			
			return REDIRECT_ADMIN_OFFICER_PAGE_VIEW;
		}
		catch(WeCareException e) {
			String emailAndPhoneMessage = e.getMessage();
			addOfficerParameters(newOfficer, model, emailAndPhoneMessage);

			return ADMIN_ADD_OFFICER_PAGE_VIEW;
		}
	}

	private void addOfficerParameters(OfficerModel newOfficer, Model model, String message) {
		model.addAttribute(MESSAGE, message);
		model.addAttribute("newOfficer", newOfficer);
		model.addAttribute(JURISDICTION, juryService.getAllJurisdictions());
	}

	@GetMapping("/change-password")
	public String changeAdminPassword(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		model.addAttribute("passwordModel", new PasswordModel());

		return ADMIN_CHANGE_PASSWORD_PAGE_VIEW;
	}

	@PostMapping("/change-password")
	public String changePassword(@ModelAttribute PasswordModel passwordModel, Model model, @AuthenticationPrincipal UserDetails userDetails, 
		RedirectAttributes redirectAttributes) {
		adminDetails(model, userDetails);
		String username = userDetails.getUsername();
		Boolean isPasswordValid = adminService.isCurrentPasswordValid(username, passwordModel.getCurrentPassword());

		if (Boolean.FALSE.equals(isPasswordValid)) {
		    model.addAttribute("passwordError", "Current password is incorrect !");
		    return ADMIN_CHANGE_PASSWORD_PAGE_VIEW;
		}
		adminService.updatePassword(username, passwordModel.getNewPassword());
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Password Changed successfully !");

		return REDIRECT_LOGIN_LOGOUT;
	}

	@GetMapping("/view-profile")
	public String viewAdminProfile(Model model, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("successMessage") String successMessage) {
		adminDetails(model, userDetails);
		Admin admin = adminService.findAdminByEmail(userDetails.getUsername());
		model.addAttribute("adminDetails", admin);
		countOfEntries(model);
		model.addAttribute(SUCCESS_MESSAGE, successMessage);

		return ADMIN_VIEW_PROFILE_PAGE_VIEW;
	}
	
	@GetMapping("/track-complaints/view-details")
	public String showComplaintDetais(@RequestParam("complaintId") int complaintId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		Complaint complaint = complService.getComplaint(complaintId);
		model.addAttribute("complaint", complaint);

		return ADMIN_VIEW_COMPLAINT_DETAILS;
	}

	@GetMapping("/add-admin")
	public String adminCreationForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		adminDetails(model, userDetails);
		model.addAttribute("admin", new AdminModel());

		return ADMIN_ADD_ADMIN_PAGE_VIEW;
	}

	@PostMapping("/add-admin")
	public String addAdmin(@ModelAttribute("admin") AdminModel adminModel, Model model, @AuthenticationPrincipal UserDetails userDetails, 
	RedirectAttributes redirectAttributes) throws Exception {
		adminDetails(model, userDetails);
		try {
			adminService.createAdmin(adminModel);
			redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Admin created successfully !");

			return REDIRECT_ADMIN_VIEW_PROFILE_PAGE_VIEW;
		} catch (WeCareException e) {
			model.addAttribute(MESSAGE, e.getMessage());
		}

		return ADMIN_ADD_ADMIN_PAGE_VIEW;
	}

	@PostMapping("/updateStatus/{complaintId}")
	public String updateStatus(@PathVariable int complaintId, @RequestParam Status newStatus, RedirectAttributes redirectAttributes, User user) throws Exception {
		complService.updateComplaintStatus(complaintId, newStatus, user);
		ResponseEntity.ok(newStatus.name());
		redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Complaint's Status is now Updated !");
		
		return "redirect:/wecare/admin/track-complaints";
	}
}