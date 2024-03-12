package com.team3.wecare.service;

import org.springframework.stereotype.Service;

import com.team3.wecare.entities.Admin;
import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.User;
import com.team3.wecare.exception.WeCareException;

@Service
public interface EmailSenderService {

	/**
	 * Generates and retrieves a one-time password (OTP) for the specified email
	 * address.
	 *
	 * @param email The email address for which to generate the OTP.
	 * @return The generated OTP as a string.
	 */
	String getotp(String email);

	/**
	 * Validates the provided OTP for a given email address.
	 *
	 * @param email The email address associated with the OTP.
	 * @param otp   The one-time password to validate.
	 * @return {@code true} if the provided OTP is valid for the given email,
	 *         {@code false} otherwise.
	 * @throws WeCareException If an error occurs during the validation process,
	 *                         such as an invalid OTP format.
	 */
	Boolean validate(String email, String otp) throws WeCareException;

	/**
	 * Generates the email content to be sent to the user regarding a complaint.
	 *
	 * @param user      The user who created the complaint.
	 * @param officer   The officer assigned to handle the complaint (if
	 *                  applicable).
	 * @param complaint The complaint details.
	 * @return The formatted email content as a String.
	 * @throws Exception If an error occurs while generating the email content.
	 */
	String getEmailContent(User user, Officer officer, Complaint complaint) throws Exception;

	/**
	 * Sends an email to the user regarding a complaint.
	 *
	 * @param user      The user who created the complaint.
	 * @param officer   The officer assigned to handle the complaint (if
	 *                  applicable).
	 * @param complaint The complaint details.
	 * @return
	 * @throws Exception If an error occurs while sending the email.
	 */
	Boolean sendEmailToUser(User user, Officer officer, Complaint complaint) throws Exception;

	/**
	 * Sends an email to the specified Officer associated with the given
	 * Jurisdiction.
	 *
	 * @param officer The Officer to whom the email will be sent.
	 * @param jury    The Jurisdiction associated with the Officer.
	 * @return true if the email is successfully sent; false otherwise.
	 * @throws Exception If an error occurs during the email sending process.
	 */
	Boolean sendEmailToOfficer(Officer officer, Jurisdiction jury) throws Exception;

	/**
	 * Retrieves the email content intended for the specified Officer associated
	 * with the given Jurisdiction.
	 *
	 * @param officer The Officer for whom the email content is generated.
	 * @param jury    The Jurisdiction associated with the Officer.
	 * @return The email content as a String.
	 * @throws Exception If an error occurs while retrieving the email content.
	 */
	String getEmaiOfficerContent(Officer officer, Jurisdiction jury) throws Exception;

	/**
	 * Retrieves the response content intended for a User based on the provided User
	 * and Complaint details.
	 *
	 * @param user      The User for whom the response content is generated.
	 * @param complaint The Complaint associated with the User's query.
	 * @return The response content as a String.
	 * @throws Exception If an error occurs while retrieving the response content.
	 */
	String getResponseContent(User user, Complaint complaint) throws Exception;

	/**
	 * Sends a response to the specified User regarding the provided Complaint.
	 *
	 * @param user      The User to whom the response will be sent.
	 * @param complaint The Complaint associated with the User's query.
	 * @return true if the response is successfully sent; false otherwise.
	 * @throws Exception If an error occurs during the response sending process.
	 */
	Boolean sendResponseToUser(User user, Complaint complaint) throws Exception;

	/**
	 * Saves a new Complaint associated with the specified User.
	 *
	 * @param complaint The Complaint to be saved.
	 * @param user      The User associated with the Complaint.
	 * @throws Exception If an error occurs during the process of saving the
	 *                   Complaint, such as database issues or validation errors.
	 */

	/**
	 * Sends the status of a given complaint to the specified user.
	 *
	 * @param user      The user to whom the status will be sent.
	 * @param complaint The complaint for which the status will be sent.
	 * @return {@code true} if the status is successfully sent, {@code false}
	 *         otherwise.
	 * @throws Exception If an error occurs during the status sending process.
	 */
	Boolean sendStatusToUser(User user, Complaint complaint) throws Exception;

	/**
	 * Retrieves the content of the status associated with a specific complaint for
	 * the given user.
	 *
	 * @param user      The user for whom the status content is requested.
	 * @param complaint The complaint for which the status content is requested.
	 * @return The content of the status associated with the complaint for the
	 *         specified user.
	 * @throws Exception If an error occurs while retrieving the status content.
	 */
	String getStatusContent(User user, Complaint complaint) throws Exception;

	/**
	 * @param Admin to whom the email notification should be sent.
	 * @return {@code true} if the email is sent successfully, {@code false}
	 *         otherwise.
	 * @throws Exception If there is an unexpected error during the email sending
	 *                   process.
	 */
	Boolean sendEmailToAdmin(Admin admin) throws Exception;

	/**
	 * Retrieves the email content intended for the specified Admin.
	 *
	 * @param admin The Admin for whom the email content is generated or retrieved.
	 * @return The email content as a String, or {@code null} if an error occurs.
	 * @throws Exception If there is an unexpected error during the content
	 *                   retrieval process.
	 */
	String getEmailContentOfAdmin(Admin admin) throws Exception;

}