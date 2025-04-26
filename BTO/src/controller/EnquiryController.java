package controller;

import Database.EnquiryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Enquiry;
import model.Project;
import model.Applicant;
import model.Application;
import model.Enquiry;

/**
 * Provides services for creating and managing enquiries within the system.
 * <p>
 * Supports submission, deletion, and retrieval of enquiries
 * by applicant, by project, or all enquiries for managers.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025 
 */
public class EnquiryController implements Interface.IEnquiryServices{
	/**
     * All enquiries stored in memory, keyed by enquiry ID.
     */
    private static Map<Integer, Enquiry> allEnquiries = EnquiryRepository.getRepository();
    
	/**
     * Submits (adds) a new enquiry into the system.
     * Assigns a unique ID and stores it.
     *
     * @param enquiry the Enquiry object to submit (will be assigned an ID)
     */
    public void submitEnquiry(Enquiry enquiry) {
    	int id = EnquiryRepository.getidToAssign();
    	enquiry.setID(id);
    	allEnquiries.put(id, enquiry);
    	System.out.println("Enquiry added!");
    }
    
	/**
     * Retrieves all enquiries submitted by a specific applicant.
     *
     * @param applicant the applicant whose enquiries to retrieve
     * @return map of enquiry IDs to Enquiry objects for that applicant
     */
    public Map<Integer, Enquiry> applicantGetEnquiries(Applicant applicant) {
    	Map<Integer, Enquiry> applicantOwnEnquiries = new HashMap<>();
    	for (Map.Entry<Integer, Enquiry> entry : allEnquiries.entrySet()) {
    		if (entry.getValue().getApplicant() == applicant) {
    			applicantOwnEnquiries.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return applicantOwnEnquiries;
    }
    
	/**
     * Deletes an existing enquiry from the system.
     *
     * @param enquiry the Enquiry object to delete
     */
    public void deleteEnquiry(Enquiry enquiry) {
    	allEnquiries.remove(enquiry.getID());
    }
    
	/**
     * Retrieves all enquiries associated with a given set of projects.
     *
     * @param projects map of project codes to Project objects to search
     * @return map of enquiry IDs to Enquiry objects matching those projects
     */
    public Map<Integer, Enquiry> getProjectsEnquiries(Map<String, Project> projects){
    	Map<Integer, Enquiry> enquiries = new HashMap<Integer, Enquiry>();
    	for (Map.Entry<String, Project> entry : projects.entrySet()) {
    		for (Map.Entry<Integer, Enquiry> entry2: allEnquiries.entrySet()) { // look through all enquiries to find matching project
    			if (entry2.getValue().getProject() == entry.getValue()) {
    				enquiries.put(entry2.getKey(), entry2.getValue());
    			}
    		}
    	}
    	return enquiries;
    }
    
	/**
     * Retrieves all enquiries in the system (for manager overview).
     *
     * @return map of all enquiry IDs to Enquiry objects
     */
    public Map<Integer, Enquiry> ManagerGetAllEnquiries(){
    	// copy allEnquiries
    	Map<Integer, Enquiry> enquiries = new HashMap<Integer, Enquiry>();
    	for (Map.Entry<Integer, Enquiry> entry : allEnquiries.entrySet()) {
    		enquiries.put(entry.getKey(), entry.getValue());
    	}
    	return enquiries;
    }
}