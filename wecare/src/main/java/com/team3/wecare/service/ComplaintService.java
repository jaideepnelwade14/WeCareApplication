package com.team3.wecare.service;
 
import java.util.List;

import com.team3.wecare.entities.Complaint;
import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.entities.Officer;
import com.team3.wecare.entities.Status;
import com.team3.wecare.entities.User;
import com.team3.wecare.models.ComplaintModel;
 
public interface ComplaintService {
 
	/**
	 * Retrieves a specific complaint based on the provided complaint ID.
	 *
	 * @param complId The ID of the complaint to retrieve.
	 * @return The complaint corresponding to the given ID, or null if not found.
	 */
	Complaint getComplaint(int complId);
 
	/**
	 * Retrieves all complaints in the system.
	 *
	 * @return A list containing all complaints in the system.
	 */
	List<Complaint> getAllComplaints();
 
	/**
	 * Creates a new complaint using the provided information.
	 *
	 * @param complaint The display model containing the details of the complaint.
	 * @param jury      The jurisdiction associated with the complaint.
	 * @param user      The user who submitted the complaint.
	 * @return The newly created complaint.
	 * @throws Exception
	 */
	Complaint createComplaint(ComplaintModel complaint, Jurisdiction jury, User user) throws Exception;
 
	/**
	 * Retrieves all complaints submitted by a specific user.
	 *
	 * @param user The user for whom to retrieve complaints.
	 * @return A list of complaints submitted by the specified user.
	 */
	List<Complaint> getComplaintsByUser(User user);
 
	/**
	 * Retrieves all complaints assigned to a specific officer.
	 *
	 * @param officer The officer for whom to retrieve assigned complaints.
	 * @return A list of complaints assigned to the specified officer.
	 */
	List<Complaint> getComplaintByOfficer(Officer officer);
 
	/**
	 * Counts the total number of complaints in the system.
	 *
	 * @return The total number of complaints in the system.
	 */
	Long countComplaints();
 
	/**
	 * Saves a new Complaint associated with the specified User.
	 *
	 * @param complaint The Complaint to be saved.
	 * @param user      The User associated with the Complaint.
	 * @throws Exception If an error occurs during the process of saving the Complaint,
	 *                   such as database issues or validation errors.
	 */
	void saveComplaint(Complaint complaint, User user) throws Exception;
 
	/**
	 * Updates the status of a Complaint identified by the given complaintId.
	 *
	 * @param complaintId The unique identifier of the Complaint to be updated.
	 * @param newStatus   The new Status to be assigned to the Complaint.
	 * @throws IllegalArgumentException If the provided complaintId is invalid or if the newStatus is null.
	 * @throws Exception                If an error occurs during the process of updating the Complaint status,
	 *                                  such as database issues or validation errors.
	 */
	void updateComplaintStatus(int complaintId, Status newStatus,User user) throws Exception;

	/**
	 * Retrieves the total count of closed complaints.
	 *
	 * This method provides information about the number of complaints that have been
	 * successfully closed or resolved within the system.
	 *
	 * @return The count of closed complaints.
	 */
	Long getCountOfClosedComplaints();

 
}