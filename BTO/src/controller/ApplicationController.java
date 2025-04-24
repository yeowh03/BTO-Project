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

public class ApplicationController implements IApplicationServices{
    private static Map<Integer, Application> allApplications = ApplicationRepository.getApplicationRepository();
    
    public void addApplication(Application app) {
    	int id = ApplicationRepository.getidToAssign();
    	app.setID(id);
    	allApplications.put(id, app);
    }
    
    public void removeApplication(int id) {
    	allApplications.remove(id);
    }
    
    // In ApplicationController.java

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
    
    public Map<Integer, Application> ApplicantGetOwnApplication(Applicant applicant){
    	Map<Integer, Application> myOwnApplications = new HashMap<>();
    	for (Map.Entry<Integer, Application> entry : allApplications.entrySet()) {
    		if (entry.getValue().getApplicant() == applicant) {
    			myOwnApplications.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return myOwnApplications;
    }
    
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