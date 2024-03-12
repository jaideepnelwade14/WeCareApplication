package com.team3.wecare.service;

import java.util.List;

import com.team3.wecare.entities.User;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.UserModel;

public interface UserService {

	/**
	 * Updates an existing user in the system.
	 *
	 * @param user The user with updated information.
	 * @return The updated user after the update operation.
	 */
	User updateUserProfile(UserModel usermodel);

	/**
	 * Retrieves a specific user based on the provided user ID.
	 *
	 * @param uid The ID of the user to retrieve.
	 * @return The user corresponding to the given ID, or null if not found.
	 */
	User getUser(int uid);

	/**
	 * Creates a new user in the system using the provided user model.
	 *
	 * @param userModel The user model containing details for the new user.
	 * @return The newly created user.
	 * @throws WeCareException If an error occurs during user creation.
	 */
	User createUser(UserModel userModel) throws WeCareException;

	/**
	 * Retrieves a user based on the specified email address.
	 *
	 * @param email The email address associated with the user.
	 * @return The user with the specified email address, or null if not found.
	 */
	User findUserByEmail(String email);

	/**
	 * Checks if the provided current password is valid for the user with the given email.
	 *
	 * @param email           The email address of the user.
	 * @param currentPassword The current password to be validated.
	 * @return True if the current password is valid, false otherwise.
	 */
	Boolean isCurrentPasswordValid(String email, String currentPassword);

	/**
	 * Updates the password for the user with the specified email address.
	 *
	 * @param email      The email address of the user for whom to update the password.
	 * @param newPassword The new password to set.
	 */
	void updatePassword(String email, String newPassword);

	/**
	 * Counts the total number of users in the system.
	 *
	 * @return The total number of users in the system.
	 */
	Long countUsers();
	
	/**
	 * Retrieves a list of all users from the database.
	 *
	 * @return A List containing all users in the database.
	 */
	List<User> getAllUsers();

	/**
	 * Retrieves a user based on the provided email and phone number.
	 *
	 * @param email The email address of the user to retrieve.
	 * @param phone The phone number of the user to retrieve.
   * @param userId The id of the user to retrieve details.
	 * @return The User corresponding to the provided email and phone, or null if not found.
	 */
	User getUserByDetails(String email, String phone, int userId);
  
}