package controller;

import Database.ApplicationRepository;
import Interface.IApplicationServices;
import model.Project;
import model.RegistrationStatus;
import model.User;
import model.Applicant;
import model.Application;
import model.ApplicationStatus;
import model.Enquiry;
import model.Officer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Manages the application's in‐memory repository of Application entities.
 * <p>
 * Provides operations to add and remove applications, as well as
 * to retrieve applications filtered by role (Applicant, Officer, Manager)
 * and by status (BOOKED, PENDING, cancelling).</p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025 
 */
public class ApplicationController implements IApplicationServices{
	/**
     * All applications stored in the system, keyed by application ID.
     */
    private static Map<Integer, Application> allApplications = ApplicationRepository.getApplicationRepository();
    
	/**
     * Adds a new application to the repository.
     *
     * @param app the Application object to add
     */
    public void addApplication(Application app) {
    	int id = ApplicationRepository.getidToAssign();
    	app.setID(id);
    	allApplications.put(id, app);
    }
    
	/**
     * Removes an application from the repository.
     *
     * @param id the ID of the application to remove
     */
    public void removeApplication(int id) {
    	allApplications.remove(id);
    }
    
    // In ApplicationController.java

	/**
     * Retrieves all successfully booked applications assigned to a particular officer.
     *
     * @param officer the Officer whose successful applications to retrieve
     * @return map of application IDs to Application objects assigned to that officer
     */
	@Override
	public Map<Integer, Application> officerGetSuccessfulApplication(Officer officer) {
    	Map<Integer, Application> successfulApps = new HashMap<>();
    	for (Map.Entry<Integer, Application> entry : allApplications.entrySet()) {
        	Application app = entry.getValue();
        	if (app.getStatus() == ApplicationStatus.SUCCESSFUL && officer.getHandledProjects().containsKey(app.getProject().getProjectName())) {
            	successfulApps.put(entry.getKey(), app);
        	}
    	}
    	return successfulApps;
	}
    
	/**
     * Retrieves all applications submitted by a specific applicant.
     *
     * @param applicant the Applicant whose applications to fetch
     * @return map of application IDs to Application objects belonging to that applicant
     */
    public Map<Integer, Application> ApplicantGetOwnApplication(Applicant applicant){
    	Map<Integer, Application> myOwnApplications = new HashMap<>();
    	for (Map.Entry<Integer, Application> entry : allApplications.entrySet()) {
    		if (entry.getValue().getApplicant() == applicant) {
    			myOwnApplications.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return myOwnApplications;
    }
    
	/**
     * Retrieves all applications with status BOOKED for the given set of projects.
     *
     * @param projects map of project codes to Project objects
     * @return map of application IDs to Application objects that are BOOKED in those projects
     */
    public Map<Integer, Application> getProjectsBookedApplications(Map<String, Project> projects){
    	Map<Integer, Application> applications = new HashMap<Integer, Application>();
    	for (Map.Entry<String, Project> entry : projects.entrySet()) {
    		for (Map.Entry<Integer, Application> entry2: allApplications.entrySet()) { // look through all applications to find matching project
    			if (entry2.getValue().getProject() == entry.getValue() && entry2.getValue().getStatus() == ApplicationStatus.BOOKED) {
    				applications.put(entry2.getKey(), entry2.getValue());
    			}
    		}
    	}
    	return applications;
    }
    
	/**
     * Retrieves all pending applications across a manager’s projects.
     *
     * @param projects map of project codes to Project objects managed by the manager
     * @return map of application IDs to Application objects with status PENDING
     */
    @Override
	public Map<Integer, Application> ManagerGetPendingApplications(Map<String, Project> projects) {
    	Map<Integer, Application> pending = new HashMap<>();
    	// Iterate all stored applications, filter by project ownership + PENDING status
    	for (Map.Entry<Integer, Application> entry : allApplications.entrySet()) {
        	Application app = entry.getValue();
        	String projName = app.getProject().getProjectName();
        	if (projects.containsKey(projName) && app.getStatus() == ApplicationStatus.PENDING) {
            	pending.put(entry.getKey(), app);
        	}
    	}
    	return pending;
	}
    
	/**
     * Retrieves all applications currently in cancellation process
     * for a manager’s projects.
     *
     * @param projects map of project codes to Project objects managed by the manager
     * @return map of application IDs to Application objects with status PENDING_CANCEL or PENDING_CANCEL_BOOKING
     */
    @Override
	public Map<Integer, Application> ManagerGetCancellingApplications(Map<String, Project> projects) {
    	Map<Integer, Application> cancelling = new HashMap<>();
    	// Iterate all stored applications, filter by project ownership + PENDING_CANCEL_BOOKING
    	for (Map.Entry<Integer, Application> entry : allApplications.entrySet()) {
        	Application app = entry.getValue();
        	String projName = app.getProject().getProjectName();
        	if (projects.containsKey(projName)) {
        		if (app.getStatus() == ApplicationStatus.PENDING_CANCEL_BOOKING || app.getStatus() == ApplicationStatus.PENDING_CANCEL) {
        			cancelling.put(entry.getKey(), app);
        		}
        	} 
    	}
    	return cancelling;
	}
}