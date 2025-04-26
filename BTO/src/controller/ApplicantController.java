package controller;

import Interface.IApplicantServices;
import Interface.IApplicationServices;
import Interface.IEnquiryServices;
import Interface.IProjectServices;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import model.Applicant;
import model.Application;
import model.ApplicationStatus;
import model.Enquiry;
import model.Project;
import model.UnitType;

/**
 * Implements all applicant‐side use cases:
 * <ul>
 *   <li>Browsing projects</li>
 *   <li>Submitting and viewing applications</li>
 *   <li>Submitting, viewing, editing, deleting enquiries</li>
 *   <li>Requesting withdrawal</li>
 * </ul>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class ApplicantController implements IApplicantServices{
	/**
     * Constructs a new ApplicantController.
     */
	public ApplicantController() {
		// no initialization required
	}
	
	/**
     * Retrieves all projects the applicant is eligible for and visible,
     * applies sorting and neighborhood filtering, then prints details.
     *
     * @param applicant the applicant browsing projects
     * @return a map of project IDs to Project objects that the applicant can view
     */
	public Map<String, Project> browseProjects(Applicant applicant) {
		IProjectServices projectSvc = new ProjectController();
		IApplicationServices applicationSvc = new ApplicationController();
		
		// 1) Get only those projects the applicant is eligible for
		Map<String, Project> eligible = projectSvc.getEligibleProjects(applicant);
		
		// 2) Filter out invisible projects
		Map<String, Project> visible = new HashMap<>();
		for (Map.Entry<String, Project> e : eligible.entrySet()) {
			if (e.getValue().isVisible()) {
				visible.put(e.getKey(), e.getValue());
			}
		}
		// 3) Re-include any they’ve applied to, even if now hidden
		Map<Integer, Application> applied = applicationSvc.ApplicantGetOwnApplication(applicant);
		for (Application a : applied.values()) {
			visible.put(a.getProject().getProjectName(), a.getProject());
		}
		// 4) Optional sorting
		if (applicant.getWantSort() == 1) {
			visible = new TreeMap<>(visible);
		}
	
		// 5) Print each project’s details, including cost
		for (Project proj : visible.values()) {
			// neighborhood / filter checks...
			if (!applicant.getNeighbourhoodFilter().isEmpty() &&
				!proj.getNeighborhood().equalsIgnoreCase(applicant.getNeighbourhoodFilter())) {
				continue;
			}
	
			System.out.printf("Project Name: %s\n", proj.getProjectName());
			System.out.printf("Neighbourhood: %s\n", proj.getNeighborhood());
	
			Map<String, UnitType> units = proj.getUnitTypes();
			String tf = applicant.getTypeFilter();
	
			// 2-Room block
			if (tf.isEmpty() || tf.equalsIgnoreCase("2-Room")) {
				UnitType u2 = units.get("2-Room");
				if (u2 != null) {
					System.out.printf("Total Number of 2-Room Flats: %d\n",    u2.getTotalUnits());
					System.out.printf("Available Number of 2-Room Flats: %d\n", u2.getAvailableUnits());
					System.out.printf("Price of 2-Room Flats: $%.2f\n",        u2.getPrice());
				}
			}
	
			// 3-Room block
			if (tf.isEmpty() || tf.equalsIgnoreCase("3-Room")) {
				UnitType u3 = units.get("3-Room");
				if (u3 != null) {
					System.out.printf("Total Number of 3-Room Flats: %d\n",    u3.getTotalUnits());
					System.out.printf("Available Number of 3-Room Flats: %d\n", u3.getAvailableUnits());
					System.out.printf("Price of 3-Room Flats: $%.2f\n",        u3.getPrice());
				}
			}
	
			System.out.printf("Opening Date: %s\n", proj.getOpeningDate());
			System.out.printf("Closing Date: %s\n", proj.getClosingDate());
			System.out.printf("HDB Manager: %s\n", proj.getAssignedManager().getName());
			System.out.println();  // blank line between projects
		}
	
		return visible;
	}

	/**
     * Allows an applicant to submit a new application if they have none pending.
     * Prints confirmation messages or errors as needed.
     *
     * @param applicant the applicant submitting the application
     */
	public void submitApplication(Applicant applicant) {
		IProjectServices ProjectController = new ProjectController();
		IApplicationServices ApplicationController = new ApplicationController();
	
		// Check if user has existing application
		Map<Integer, Application> myApplications = ApplicationController.ApplicantGetOwnApplication(applicant);
		if (myApplications.isEmpty()) {
			System.out.println("No applications has been submitted.");
		}
		for (Map.Entry<Integer, Application> entry : myApplications.entrySet()) {
			if (entry.getValue().getStatus() == ApplicationStatus.PENDING || entry.getValue().getStatus() == ApplicationStatus.SUCCESSFUL || entry.getValue().getStatus() == ApplicationStatus.BOOKED) {
				System.out.println("You already have a pending application. Cannot apply again.");
				return;
			}
		}
	
		// Eligibility check
		int age = applicant.getAge();
		String status = applicant.getMaritalStatus();
	
		if (status.equalsIgnoreCase("Single") && age < 35) {
			System.out.println("As a single applicant, you must be at least 35 years old to apply.");
			return;
		} else if (status.equalsIgnoreCase("Married") && age < 21) {
			System.out.println("As a married applicant, you must be at least 21 years old to apply.");
			return;
		}
	
		// Filter eligible projects
		Map<String, Project> eligibleProjects = ProjectController.getEligibleProjects(applicant);
	
		// Remove invisible projects
		Iterator<Map.Entry<String, Project>> iter = eligibleProjects.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Project> entry = iter.next();
			if (!entry.getValue().isVisible()) {
				iter.remove();
			}
		}
	
		// Remove those outside of application period
		LocalDate today = LocalDate.now();
		Iterator<Map.Entry<String, Project>> iter2 = eligibleProjects.entrySet().iterator();
		while (iter2.hasNext()) {
			Map.Entry<String, Project> entry = iter2.next();
			if (today.isBefore(entry.getValue().getOpeningDate()) || today.isAfter(entry.getValue().getClosingDate())) {
				iter2.remove();
			}
		}
	
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the project name you want to apply for: ");
		String pName = sc.nextLine().trim();
	
		if (!eligibleProjects.containsKey(pName)) {
			System.out.println("Invalid project name.");
			return;
		}
	
		// Decide flat type based on marital status
		Project chosen = eligibleProjects.get(pName);
		System.out.println("Enter '2' for 2-Room" + 
			(status.equalsIgnoreCase("Married") ? ", '3' for 3-Room." : "."));
	
		try {
			int flatType = sc.nextInt();
			sc.nextLine();
	
			if (flatType == 2) {
				Application app = new Application(applicant, chosen, "2-Room");
				ApplicationController.addApplication(app);
				System.out.println("2-Room application submitted.");
			} else if (flatType == 3 && status.equalsIgnoreCase("Married")) {
				Application app = new Application(applicant, chosen, "3-Room");
				ApplicationController.addApplication(app);
				System.out.println("3-Room application submitted.");
			} else {
				System.out.println("You are not eligible to apply for this flat type.");
			}
	
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Please enter 2 or 3.");
		}
	}

	/**
     * Displays the status of all applications by this applicant,
     * including timestamp, project name, flat type, and status.
     *
     * @param applicant the applicant whose applications are being viewed
     */
	public void viewApplicationStatus(Applicant applicant) {
		IApplicationServices ApplicationController = new ApplicationController();
		
		Map<Integer, Application> myApplications = ApplicationController.ApplicantGetOwnApplication(applicant);

		if (myApplications.isEmpty()) {
			System.out.println("No applications has been submitted.");
			return;
		}
		for (Map.Entry<Integer, Application> entry : myApplications.entrySet()) {
			System.out.printf("Application ID: %d\n", entry.getValue().getApplicationId());
			System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());			
			System.out.printf("Flat Type: %s\n", entry.getValue().getUnitType());
			System.out.printf("Status: %s\n", entry.getValue().getStatus());
		} 
	} 

	/**
     * Creates a new enquiry from this applicant for a chosen project,
     * reading the question text from console input.
     *
     * @param applicant the applicant submitting the enquiry
     */
	public void submitEnquiry(Applicant applicant) {
		IEnquiryServices EnquiryController = new EnquiryController();
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter question: ");
        String question = scanner.nextLine().trim();
        
        // only can enquire about projects applicant can see
        System.out.print("Enter Project Name: ");
        String projName = scanner.nextLine().trim();
        
        Map<String, Project> visibleProj = this.browseProjects(applicant);
        for (Map.Entry<String, Project> entry : visibleProj.entrySet()) {
        	if (entry.getKey().equalsIgnoreCase(projName)) {
        		Enquiry enquiry = new Enquiry(question, entry.getValue(), applicant);
        		EnquiryController.submitEnquiry(enquiry);
        		return;
        	}
        }
        
        System.out.println("Invalid Projct Name.");
	}
	
	/**
     * Displays all enquiries submitted by this applicant,
     * including any replies from officers or managers.
     *
     * @param applicant the applicant whose enquiries are being listed
     */
	public void viewEnquiries(Applicant applicant) {
		IEnquiryServices EnquiryController = new EnquiryController();
		
		Map<Integer, Enquiry> myEnquiries = EnquiryController.applicantGetEnquiries(applicant);
		if (myEnquiries.isEmpty()) {
			System.out.println("No enquiries has been submitted.");
		}
		for (Map.Entry<Integer, Enquiry> entry : myEnquiries.entrySet()) {
			System.out.printf("Enquiry ID: %d\n", entry.getValue().getID());
			System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());
			System.out.printf("Question: %s\n", entry.getValue().getQuestion());
			System.out.printf("Reply: %s", entry.getValue().getReply());
		}
	}

	/**
     * Allows the applicant to edit the text of an existing enquiry
     * before it has been answered, re‐reading input from console.
     *
     * @param applicant the applicant editing their enquiry
     */
	public void editEnquiry(Applicant applicant) {
		IEnquiryServices EnquiryController = new EnquiryController();
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the enquiry to edit: ");
        
        try {
            int idToEdit = Integer.parseInt(scanner.nextLine().trim());
            Map<Integer, Enquiry> myEnquiries = EnquiryController.applicantGetEnquiries(applicant);
            for (Map.Entry<Integer, Enquiry> entry : myEnquiries.entrySet()) {
            	if (entry.getValue().getApplicant() == applicant && entry.getValue().getID() == idToEdit) {
            		System.out.print("Enter new detail: ");
                    String newQuestion = scanner.nextLine().trim();
                    entry.getValue().setQuestion(newQuestion);
                    System.out.println("Edit successfully!");
                    return;
            	}
            }
            System.out.println("Enquiry ID not found.");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
	}
	/**
     * Deletes an existing enquiry for this applicant,
     * after prompting for the enquiry ID.
     *
     * @param applicant the applicant deleting their enquiry
     */
	public void deleteEnquiry(Applicant applicant) {
		IEnquiryServices EnquiryController = new EnquiryController();
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the enquiry to delete: ");
        
        try {
            int idToDelete = Integer.parseInt(scanner.nextLine().trim());
            Map<Integer, Enquiry> myEnquiries = EnquiryController.applicantGetEnquiries(applicant);
            for (Map.Entry<Integer, Enquiry> entry : myEnquiries.entrySet()) {
            	if (entry.getValue().getApplicant() == applicant && entry.getValue().getID() == idToDelete) {
            		EnquiryController.deleteEnquiry(entry.getValue());
                    System.out.println("Deleted successfully!");
                    return;
            	}
            }
            System.out.println("Enquiry ID not found.");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
	}

	/**
     * Requests withdrawal of a booked application,
     * changing its status to PENDING_CANCEL or PENDING_CANCEL_BOOKING.
     *
     * @param applicant the applicant requesting withdrawal
     */
	public void requestWithdrawal(Applicant applicant) {
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the application to delete: ");
        
        IApplicationServices ApplicationController = new ApplicationController();
        
        try {
        	int idToWithdraw = Integer.parseInt(scanner.nextLine().trim());
        	Map<Integer, Application> myApplications = ApplicationController.ApplicantGetOwnApplication(applicant);
    		for (Map.Entry<Integer, Application> entry : myApplications.entrySet()) {
    			if (entry.getValue().getApplicationId() == idToWithdraw) {
    				if (entry.getValue().getStatus() == ApplicationStatus.BOOKED) {
    					entry.getValue().setStatus(ApplicationStatus.PENDING_CANCEL_BOOKING);
    				} else {
    					entry.getValue().setStatus(ApplicationStatus.PENDING_CANCEL);
    				}
    				System.out.println("Request sent!");
    				return;
    			}
    		}
    		
    		System.out.println("Application ID not found.");
        }
        
        catch (NumberFormatException e) {
        	System.out.println("Invalid input. Please enter a valid number.");
        }
	}
}
