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

public class EnquiryController implements Interface.IEnquiryServices{
    private static Map<Integer, Enquiry> allEnquiries = EnquiryRepository.getRepository();
    
    public void submitEnquiry(Enquiry enquiry) {
    	int id = EnquiryRepository.getidToAssign();
    	enquiry.setID(id);
    	allEnquiries.put(id, enquiry);
    	System.out.println("Enquiry added!");
    }
    
    public Map<Integer, Enquiry> applicantGetEnquiries(Applicant applicant) {
    	Map<Integer, Enquiry> applicantOwnEnquiries = new HashMap<>();
    	for (Map.Entry<Integer, Enquiry> entry : allEnquiries.entrySet()) {
    		if (entry.getValue().getApplicant() == applicant) {
    			applicantOwnEnquiries.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return applicantOwnEnquiries;
    }
    
    public void deleteEnquiry(Enquiry enquiry) {
    	allEnquiries.remove(enquiry.getID());
    }
    
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
    
    public Map<Integer, Enquiry> ManagerGetAllEnquiries(){
    	// copy allEnquiries
    	Map<Integer, Enquiry> enquiries = new HashMap<Integer, Enquiry>();
    	for (Map.Entry<Integer, Enquiry> entry : allEnquiries.entrySet()) {
    		enquiries.put(entry.getKey(), entry.getValue());
    	}
    	return enquiries;
    }
}