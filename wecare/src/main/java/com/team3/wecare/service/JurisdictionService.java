package com.team3.wecare.service;

import java.util.List;

import com.team3.wecare.entities.Jurisdiction;
import com.team3.wecare.models.JurisdictionModel;

public interface JurisdictionService {

	/**
	 * Updates an existing jurisdiction in the system.
	 *
	 * @param jurisdiction The jurisdiction object containing updated information.
	 * @return The updated jurisdiction after the update operation.
	 */
	Jurisdiction updateJurisdiction(JurisdictionModel jurisdiction);

	/**
	 * Retrieves a specific jurisdiction based on the provided jurisdiction ID.
	 *
	 * @param juryId The ID of the jurisdiction to retrieve.
	 * @return The jurisdiction corresponding to the given ID, or null if not found.
	 */
	Jurisdiction getJurisdiction(int juryId);

	/**
	 * Retrieves all jurisdictions in the system.
	 *
	 * @return A list containing all jurisdictions in the system.
	 */
	List<Jurisdiction> getAllJurisdictions();

	/**
	 * Creates a new jurisdiction using the provided information.
	 *
	 * @param juryModel The create model containing the details for the new jurisdiction.
	 * @return The newly created jurisdiction.
	 */
	Jurisdiction createJurisdiction(JurisdictionModel juryModel);

	/**
	 * Deletes a jurisdiction from the system based on the provided jurisdiction ID.
	 *
	 * @param jurisdictionId The ID of the jurisdiction to delete.
	 * @return A message indicating the result of the deletion operation.
	 */
	String deleteJurisdictionById(int jurisdictionId);

	/**
	 * Retrieves a jurisdiction based on the specified area, ward, and layout details.
	 *
	 * @param area  The area of the jurisdiction.
	 * @param ward  The ward of the jurisdiction.
	 * @param layout The layout of the jurisdiction.
	 * @return The jurisdiction matching the specified details, or null if not found.
	 */
	Jurisdiction getJuryByDetails(String area, String ward, String layout);

	/**
	 * Retrieves a list of ward names for a given area.
	 *
	 * @param area The area for which to retrieve ward names.
	 * @return A list of ward names within the specified area.
	 */
	List<String> getWardsByArea(String area);

	/**
	 * Retrieves a list of layout names for a given ward.
	 *
	 * @param ward The ward for which to retrieve layout names.
	 * @return A list of layout names within the specified ward.
	 */
	List<String> getLayoutByWard(String ward);

	/**
	 * Retrieves a list of distinct area names across all jurisdictions.
	 *
	 * @return A list of distinct area names.
	 */
	List<String> getDistinctArea();

	/**
	 * Counts the total number of jurisdictions in the system.
	 *
	 * @return The total number of jurisdictions in the system.
	 */
	Long countJurisdictions();

}