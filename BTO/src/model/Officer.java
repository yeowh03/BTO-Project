package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HDB officer, who is also an Applicant with additional responsibilities.
 * <p>
 * Tracks the BTO projects this officer is assigned to handle.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Officer extends Applicant{
	
	/**
     * Map of project names to Project objects that this officer manages.
     */
	private Map<String, Project> handledProjects;
	
	/**
     * Constructs a new Officer with the given personal details.
     * Initializes the handledProjects map to empty.
     *
     * @param name           the officer’s name
     * @param nric           the officer’s NRIC (unique identifier)
     * @param age            the officer’s age
     * @param maritalStatus  the officer’s marital status ("Single" or "Married")
     * @param password       the officer’s login password
     */
	public Officer(String name, String nric, int age, String maritalStatus, String password){
		super(name, nric, age, maritalStatus, password);
		handledProjects = new HashMap<>();
	}
	
	/**
     * Assigns a new project for this officer to handle.
     *
     * @param name the project name key
     * @param p    the Project object to add
     */
	public void addHandledProject(String name, Project p) {
		handledProjects.put(name, p);
	}
	
	/**
     * Returns the map of projects this officer is managing.
     *
     * @return map of project names to Project objects
     */
	public Map<String, Project> getHandledProjects() {
		return handledProjects;
	}
}

