package controller;

import Interface.IApplicationServices;
import Interface.IEnquiryServices;
import Interface.IOfficerServices;
import Interface.IProjectServices;
import Interface.IRegistrationServices;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import model.Application;
import model.ApplicationStatus;
import model.Enquiry;
import model.Officer;
import model.Project;
import model.Registration;
import model.UnitType;

/**
 * Implements all officer‐side operations:
 * <ul>
 *   <li>Submitting applications</li>
 *   <li>Processing flat selection</li>
 *   <li>Registering for projects</li>
 *   <li>Viewing registration status and project details</li>
 *   <li>Handling and replying to enquiries</li>
 *   <li>Generating booking receipts</li>
 * </ul>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class OfficerController extends ApplicantController implements IOfficerServices{
	
	/**
     * Constructs a new OfficerController.
     */
	public OfficerController() {
		
	}
	
	/**
     * Allows an officer to submit an application for a booked flat.
     *
     * @param officer the Officer performing the submission
     */
	public void submitApplication(Officer officer) {
		
		IProjectServices ProjectController = new ProjectController();
		IRegistrationServices RegistrationController = new RegistrationController();
		IApplicationServices ApplicationController = new ApplicationController();
		
		// check if have pending application
		
		Map<Integer, Application> myApplications = ApplicationController.ApplicantGetOwnApplication(officer);
		for (Map.Entry<Integer, Application> entry : myApplications.entrySet()) {
			if (entry.getValue().getStatus() == ApplicationStatus.PENDING || entry.getValue().getStatus() == ApplicationStatus.SUCCESSFUL || entry.getValue().getStatus() == ApplicationStatus.BOOKED) {
				System.out.println("Cannot apply multiple projects!");
				return;
			};
		}
		
		// Eligibility check
		int age = officer.getAge();
		String status = officer.getMaritalStatus();
			
		if (status.equalsIgnoreCase("Single") && age < 35) {
			System.out.println("As a single applicant, you must be at least 35 years old to apply.");
			return;
		} else if (status.equalsIgnoreCase("Married") && age < 21) {
			System.out.println("As a married applicant, you must be at least 21 years old to apply.");
			return;
		}
		
		// get projects for my target group
		Map<String, Project> eligibleProjects = ProjectController.getEligibleProjects(officer);
		
		// filter out those without visibility
		Iterator<Map.Entry<String, Project>> iterator = eligibleProjects.entrySet().iterator();
		while (iterator.hasNext()) {
		    Map.Entry<String, Project> entry = iterator.next();
		    if (!entry.getValue().isVisible()) {
		        iterator.remove();
		    }
		}
		
		// filter out those not within application period	
		LocalDate today = LocalDate.now();
		Iterator<Map.Entry<String, Project>> iterator2 = eligibleProjects.entrySet().iterator();
		while (iterator2.hasNext()) {
		    Map.Entry<String, Project> entry = iterator2.next();
		    LocalDate openingDate = entry.getValue().getOpeningDate();
		    LocalDate closingDate = entry.getValue().getClosingDate();

		    if (today.isBefore(openingDate) || today.isAfter(closingDate)) {
		        iterator2.remove(); //
		    }
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the project name you want to apply for: ");
		String pName = sc.nextLine().trim();
		if (eligibleProjects.containsKey(pName)) {
			
			// officer checks if he already registered for this project before
			Map<Integer, Registration> registrations = RegistrationController.OfficerGetOwnRegistration(officer);
			for (Map.Entry<Integer, Registration> entry : registrations.entrySet()) {
				if (entry.getValue().getProject().getProjectName().equals(pName)) {
					System.out.println("ALREADY REGISTERED FOR THIS PROJECT BEFORE");
					return;
				}
			}	
			
			System.out.println("Enter '2' for 2-Room" + (status.equalsIgnoreCase("Married") ? ", '3' for 3-Room." : "."));
			
			try {
				int flatType = sc.nextInt();
				sc.nextLine();
				
				if (flatType == 2) {
					Application app = new Application(officer, eligibleProjects.get(pName), "2-Room");
					ApplicationController.addApplication(app);
					System.out.println("2-Room application submitted.");
				} else if (flatType == 3) {
					Application app = new Application(officer, eligibleProjects.get(pName), "3-Room");
					ApplicationController.addApplication(app);
					System.out.println("3-Room application submitted.");
				} else {
					System.out.println("Invalid room type.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		} else {
			System.out.println("Invalid Project Name.");
		}
	}
	
	/**
     * Processes the flat‐selection step after a booking has been made.
     *
     * @param officer the Officer handling flat selection
     */
	public void processFlatSelection(Officer officer) {
		Scanner sc = new Scanner(System.in);
		IApplicationServices appSvc = new ApplicationController();
	
		// 1) Fetch all successful apps for this officer
		Map<Integer, Application> apps = 
			appSvc.officerGetSuccessfulApplication(officer);
	
		if (apps.isEmpty()) {
			System.out.println("No successful applications to book.");
			return;
		}
	
		// 2) Display them
		System.out.println("Successful Applications:");
		for (Map.Entry<Integer, Application> e : apps.entrySet()) {
			Application a = e.getValue();
			System.out.printf("  ID: %d | Applicant: %s | Project: %s | Flat: %s\n",
				e.getKey(),
				a.getApplicant().getName(),
				a.getProject().getProjectName(),
				a.getUnitType()
			);
		}
	
		// 3) Choose one to book
		System.out.print("Enter application ID to confirm booking: ");
		int id;
		try {
			id = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException ex) {
			System.out.println("Invalid ID. Returning to menu.");
			return;
		}
	
		if (!apps.containsKey(id)) {
			System.out.println("ID not found among successful applications.");
			return;
		}
	
		Application chosen = apps.get(id);
		// 4) Mark as booked
		chosen.setStatus(ApplicationStatus.BOOKED);
		System.out.println("Application ID " + id + " is now BOOKED.");
	
		// 5) Decrement that unit’s inventory
		UnitType ut = chosen.getProject().getUnitTypes().get(chosen.getUnitType());
		if (ut != null) {
			ut.assignUnit();
		}
		
		// 6) set flat type
		chosen.getApplicant().setflatTypeBooked(chosen.getProject().getProjectName() + chosen.getUnitType());
	}

	/**
     * Registers this officer to manage a new BTO project,
     * ensuring no date overlaps with existing assignments.
     *
     * @param officer the Officer registering for the project
     */
    public void registerForProject(Officer officer) {
    	IProjectServices ProjectController = new ProjectController();
    	IRegistrationServices RegistrationController = new RegistrationController();
    	IApplicationServices ApplicationController = new ApplicationController();
    	
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the project name to register: ");
        String projectName = sc.nextLine().trim();
        
        // retrieve the project
        Project targetProject = ProjectController.getProject(projectName);
        if (targetProject == null) {
        	System.out.println("Project not found.");
        	return;
        }
        
        // check for overlapping dates
        Map<String, Project> assignedProjects = officer.getHandledProjects();
        for(Map.Entry<String, Project> entry : assignedProjects.entrySet()) {
        	if (!entry.getValue().getOpeningDate().isAfter(targetProject.getClosingDate()) && !targetProject.getOpeningDate().isAfter(entry.getValue().getClosingDate())) { // overlap
        		System.out.println("Cannot handle multiple projects.");
        		return;
        	}
        }
        
        // now check if he is an applicant
        Map<Integer, Application> myApplications = ApplicationController.ApplicantGetOwnApplication(officer);
        for(Map.Entry<Integer, Application> entry : myApplications.entrySet()) {
        	if (entry.getValue().getApplicant() == officer) {
        		System.out.println("Cannot register for applied projects!");
        		return;
        	}
        }
        
        // check if registered before
        Map<Integer, Registration> myRegistrations = RegistrationController.OfficerGetOwnRegistration(officer);

        for(Map.Entry<Integer, Registration> entry : myRegistrations.entrySet()) {
        	if (entry.getValue().getProject() == targetProject) {
        		System.out.println("Registered before.");
        		return;
        	}
        }
        
        // register
        Registration newRegistration = new Registration(targetProject, officer);
        RegistrationController.addRegistration(newRegistration);
    }

	/**
     * Views the status of all registrations this officer has made.
     *
     * @param officer the Officer whose registrations are being viewed
     */
    public void viewRegistrationStatus(Officer officer) {
    	IRegistrationServices RegistrationController = new RegistrationController();
    	
    	Map<Integer, Registration> myRegistrations = RegistrationController.OfficerGetOwnRegistration(officer);

		if (myRegistrations.isEmpty()) {
			System.out.println("No registrations has been submitted.");
			return;
		}
    	for(Map.Entry<Integer, Registration> entry : myRegistrations.entrySet()) {
    		System.out.printf("Registration ID: %d\n", entry.getKey());
    		System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());
    		System.out.printf("Status: %s\n", entry.getValue().getStatus());
    		System.out.println("------");
    	}
    }

	/**
     * Displays detailed information for projects this officer handles,
     * including open/close dates and assigned manager.
     *
     * @param officer the Officer viewing project details
     */
    public void viewProjectDetails(Officer officer) {
    	IProjectServices ProjectController = new ProjectController();
    	
    	Map<String, Project> assignedProjects = officer.getHandledProjects();
		if (assignedProjects.isEmpty()){
			System.out.println("No assigned project");
			return;
		}
    	if (officer.getWantSort() == 1) {
			assignedProjects = new TreeMap<>(assignedProjects);
    	}
			
		for(Map.Entry<String, Project> entry : assignedProjects.entrySet()) {
			if (!officer.getNeighbourhoodFilter().isEmpty() && !entry.getValue().getNeighborhood().equalsIgnoreCase(officer.getNeighbourhoodFilter())) {
				continue;
			}
			System.out.printf("Project Name: %s\n", entry.getValue().getProjectName());
            System.out.printf("Neighbourhood: %s\n", entry.getValue().getNeighborhood());
            if (officer.getTypeFilter().isEmpty()) {
            	System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getTotalUnits());
	            System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getAvailableUnits());
				//add price of 2-room
	            System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getTotalUnits());
	            System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getAvailableUnits());
				//add price of 3-room
            } else if (officer.getTypeFilter().equalsIgnoreCase("2-Room")) {
            	System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getTotalUnits());
	            System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getAvailableUnits());
	            System.out.printf("Price of 2-Room Flats: $%.2f\n", entry.getValue().getUnitTypes().get("2-Room").getPrice());
            } else if (officer.getTypeFilter().equalsIgnoreCase("3-Room")) {
            	System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getTotalUnits());
	            System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getAvailableUnits());
	            System.out.printf("Price of 2-Room Flats: $%.2f\n", entry.getValue().getUnitTypes().get("3-Room").getPrice());
            }        
            System.out.printf("Opening Date: %s\n", entry.getValue().getOpeningDate());
            System.out.printf("Closing Date: %s\n", entry.getValue().getClosingDate());
            System.out.printf("HDB Manager: %s\n", entry.getValue().getAssignedManager().getName());
		}
    }

	/**
     * Retrieves and lists all enquiries submitted for projects
     * that this officer is responsible for.
     *
     * @param officer the Officer viewing project enquiries
     */
    public void viewProjectEnquiries(Officer officer) {
    	IProjectServices ProjectController = new ProjectController();
    	IEnquiryServices EnquiryController = new EnquiryController();
    	
    	Map<String, Project> assignedProjects = officer.getHandledProjects();
    	
    	Map<Integer, Enquiry> enquiries = EnquiryController.getProjectsEnquiries(assignedProjects);

		if (enquiries.isEmpty()) {
			System.out.println("No enquiries has been submitted.");
		}
    	
    	for(Map.Entry<Integer, Enquiry> entry : enquiries.entrySet()) {
    		System.out.printf("Enquiry ID: %d\n", entry.getKey());
    		System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());
    		System.out.printf("Question: %s\n", entry.getValue().getQuestion());
    		System.out.printf("Reply: %s\n", entry.getValue().getReply());
    		System.out.println("------");
    	}
    }

	/**
     * Allows the officer to reply to enquiries raised by applicants.
     *
     * @param officer the Officer responding to enquiries
     */
    public void replyToEnquiries(Officer officer) {
    	IProjectServices ProjectController = new ProjectController();
    	IEnquiryServices EnquiryController = new EnquiryController();
    	
    	Map<String, Project> assignedProjects = officer.getHandledProjects();
    	
    	Map<Integer, Enquiry> enquiries = EnquiryController.getProjectsEnquiries(assignedProjects);
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter the enquiry ID you wish to reply: ");
    	try {
    		int id = sc.nextInt();
    		sc.nextLine();
    		if (enquiries.containsKey(id)) {
    			System.out.println("Enter response: ");
    			String reply = sc.nextLine().trim();
    			enquiries.get(id).setReply(reply);
    			System.out.println("Enquiry replied.");
    		} else {
    			System.out.println("Enquiry ID not found.");
    		}
    	}catch (InputMismatchException e) {
    		System.out.println("Invalid input!");
    	}
    }
    
	/**
     * Generates and prints a receipt for a completed booking,
     * including applicant details and flat information.
     *
     * @param officer the Officer generating the receipt
     */
    public void generateReceipt(Officer officer) {
    	IProjectServices ProjectController = new ProjectController();
    	IApplicationServices ApplicationController = new ApplicationController();
    	
    	Map<String, Project> assignedProjects = officer.getHandledProjects();
    	
    	Map<Integer, Application> applications = ApplicationController.getProjectsBookedApplications(assignedProjects);

		if (applications.isEmpty()) {
			System.out.println("No bookings found.");
		}
    	
    	for(Map.Entry<Integer, Application> entry : applications.entrySet()) {
    		System.out.printf("Applicant Name: %s\n", entry.getValue().getApplicant().getName());
    		System.out.printf("Applicant NRIC: %s\n", entry.getValue().getApplicant().getNric());
    		System.out.printf("Applicant age: %d\n", entry.getValue().getApplicant().getAge());
    		System.out.printf("Applicant Marital Status: %s\n", entry.getValue().getApplicant().getMaritalStatus());
    		System.out.printf("Flat Type Booked: %s\n", entry.getValue().getUnitType());
    		
    		System.out.println("Project Details");
    		System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());
            System.out.printf("Neighbourhood: %s\n", entry.getValue().getProject().getNeighborhood());
     
            System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getProject().getUnitTypes().get("2-Room").getTotalUnits());
	        System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getProject().getUnitTypes().get("2-Room").getAvailableUnits());
	        System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getProject().getUnitTypes().get("3-Room").getTotalUnits());
	        System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getProject().getUnitTypes().get("3-Room").getAvailableUnits());
      
            System.out.printf("Opening Date: %s\n", entry.getValue().getProject().getOpeningDate());
            System.out.printf("Closing Date: %s\n", entry.getValue().getProject().getClosingDate());
            System.out.printf("HDB Manager: %s\n", entry.getValue().getProject().getAssignedManager().getName());
            System.out.println("------");
    	}
    }
}












