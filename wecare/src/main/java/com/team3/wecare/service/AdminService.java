package com.team3.wecare.service;

import com.team3.wecare.entities.Admin;
import com.team3.wecare.exception.WeCareException;
import com.team3.wecare.models.AdminModel;

public interface AdminService {

    /**
     * Finds an admin by their email.
     *
     * @param email The email address of the admin to be found.
     * @return The admin corresponding to the provided email, or null if not found.
     */
    Admin findAdminByEmail(String email);

    /**
     * Checks if the provided current password is valid for the admin with the given email.
     *
     * @param email           The email address of the Admin.
     * @param currentPassword The current password to be validated.
     * @return A Boolean indicating the validity of the current password:
     *         {@code true} if the current password is valid,
     *         {@code false} if the current password is not valid,
     *         {@code null} if the validation result is inconclusive or not applicable.
     */
    Boolean isCurrentPasswordValid(String email, String currentPassword);


    /**
     * Updates the password for the admin with the specified email.
     *
     * @param email      The email address of the admin.
     * @param newPassword The new password to be set.
     */
    void updatePassword(String email, String newPassword);
    
    /**
     * Creates a new admin based on the provided AdminModel.
     *
     * @param adminModel The AdminModel containing information for creating the admin.
     * @return The created admin.
     * @throws WeCareException If an exception occurs during the admin creation process.
     *                        The exception provides details about the error.
     * @throws Exception 
     */
    Admin createAdmin(AdminModel adminModel) throws WeCareException, Exception;

}