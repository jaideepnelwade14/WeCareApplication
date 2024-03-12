package com.team3.wecare.service;

import java.util.List;

import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.OfficerModel;

public interface OfficerService {

	/**
	 * Updates an existing officer in the system based on the provided officer creation model,
	 * existing officer information, and associated jurisdiction.
	 *
	 * @param officerCreateModel The officer creation model containing updated officer details.
	 * @param officer            The existing officer to be updated.
	 * @param jury               The jurisdiction associated with the officer.
	 * @return The updated officer after the update operation.
	 */
	Officer updateOfficer(OfficerModel officerCreateModel, Jurisdiction jury);

	/**
	 * Retrieves a specific officer based on the provided officer ID.
	 *
	 * @param officerId The ID of the officer to retrieve.
	 * @return The officer corresponding to the given ID, or null if not found.
	 */
	Officer getOfficer(int officerId);

	/**
	 * Retrieves all officers in the system.
	 *
	 * @return A list containing all officers in the system.
	 */
	List<Officer> getAllOfficers();

	/**
	 * Deletes an officer from the system based on the provided officer ID.
	 *
	 * @param officerId The ID of the officer to delete.
	 * @return A message indicating the result of the deletion operation.
	 */
	String deleteOfficer(int officerId);

	/**
	 * Creates a new officer in the system using the provided officer creation model and associated jurisdiction.
	 *
	 * @param newOfficer The officer creation model containing details for the new officer.
	 * @param jury       The jurisdiction associated with the new officer.
	 * @return The newly created officer.
	 */
	Officer createOfficer(OfficerModel newOfficer, Jurisdiction jury)throws Exception, WeCareException;

	/**
	 * Retrieves an officer based on the specified email address.
	 *
	 * @param email The email address associated with the officer.
	 * @return The officer with the specified email address, or null if not found.
	 */
	Officer findOfficerByEmail(String email);

	/**
	 * Retrieves an officer based on the specified phone number.
	 *
	 * @param phone The phone number associated with the officer.
	 * @return The officer with the specified phone number, or null if not found.
	 */
	Officer findOfficerByPhoneNumber(String phone);

	/**
	 * Checks if the provided current password is valid for the officer with the given email.
	 *
	 * @param email           The email address of the officer.
	 * @param currentPassword The current password to be validated.
	 * @return True if the current password is valid, false otherwise.
	 */
	Boolean isCurrentPasswordValid(String email, String currentPassword);

	/**
	 * Updates the password for the officer with the specified email address.
	 *
	 * @param email      The email address of the officer for whom to update the password.
	 * @param newPassword The new password to set.
	 */
	void updatePassword(String email, String newPassword);

	/**
	 * Counts the total number of officers in the system.
	 *
	 * @return The total number of officers in the system.
	 */
	Long countOfficers();
	
	/**
	 * Updates the profile of an officer based on the provided OfficerModel.
	 *
	 * @param officerModel The OfficerModel containing updated information for the officer.
	 * @return The updated Officer object representing the officer's profile.
	 */
	Officer updateOfficerProfile(OfficerModel officerModel);

	/**
	 * Retrieves an officer based on the provided email and phone number.
	 *
	 * @param email The email address of the officer to retrieve.
	 * @param phone The phone number of the officer to retrieve.
   * @param officerId The id of officer whose details to retrieve.
	 * @return The Officer corresponding to the provided email and phone, or null if not found.
	 */
	Officer getOfficerByDetails(String email, String phone, int officerId);
  
}