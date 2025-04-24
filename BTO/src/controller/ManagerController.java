package controller;

import Interface.IApplicationServices;
import Interface.IEnquiryServices;
import Interface.IManagerServices;
import Interface.IProjectServices;
import Interface.IRegistrationServices;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import model.Application;
import model.ApplicationStatus;
import model.Enquiry;
import model.Manager;
import model.Officer;
import model.Project;
import model.Registration;
import model.RegistrationStatus;
import model.UnitType;

public class ManagerController implements IManagerServices{
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
	
	public ManagerController() {
	}
	
	public void createProject(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		try {
			// ask for application opening and closing date
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter application opening date (M/D/YYYY): ");
			String openingDate = sc.nextLine().trim();
			System.out.println("Enter application closing date (M/D/YYYY): ");
			String closingDate = sc.nextLine().trim();
			LocalDate opening = LocalDate.parse(openingDate, DATE_FORMATTER);
			LocalDate closing = LocalDate.parse(closingDate, DATE_FORMATTER);
			
			// check if currently handled projects overlap with this opening and closing date range
			Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
			for(Map.Entry<String, Project> entry : myProjects.entrySet()) {
				if (!opening.isAfter(entry.getValue().getClosingDate()) && !entry.getValue().getOpeningDate().isAfter(closing)) { // overlap found
					System.out.println("Cannot have multiple projects in the same application range");
					return;
				}
			}
			
			System.out.print("Enter Project Name: ");
            String projectName = sc.nextLine().trim();
            
            System.out.print("Enter Neighborhood: ");
            String neighborhood = sc.nextLine().trim();
            
            System.out.print("Enter number of 2-Room units: ");
            int twoRoom = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter price for 2-Room units: ");
            double twoRoomPrice = Double.parseDouble(sc.nextLine().trim());
           
            System.out.print("Enter number of 3-Room units: ");
            int threeRoom = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter price for 3-Room units: ");
            double threeRoomPrice = Double.parseDouble(sc.nextLine().trim());
            
            System.out.print("Enter number of HDB Officers: ");
            int officerSlots = Math.min(10, Integer.parseInt(sc.nextLine().trim()));
            
            Project new_project = new Project(projectName, neighborhood, opening, closing, manager, officerSlots);
            new_project.addUnitType("2-Room", twoRoom, twoRoomPrice);
            new_project.addUnitType("3-Room", threeRoom, threeRoomPrice);
            
            if (ProjectController.addProject(new_project)) {
                System.out.println("Project created.");
            } else {
                System.out.println("Project name may already exist.");
            }
			
		} catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("Error creating project: " + e.getMessage());
        }	
	}
	
	public void editProject(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the project name to edit: ");
		String name = sc.nextLine().trim();
		
		Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
		if (myProjects.isEmpty()) {
			System.out.println("No projects under your management to edit.");
			return;
		}
		if (myProjects.containsKey(name)) {
			Project proj = myProjects.get(name);
			
			while (true) {
				System.out.println("1. Edit Application Period");
	            System.out.println("2. Edit Officer Slots");
	            System.out.println("3. Exit");
	
	            try {
	                int choice = Integer.parseInt(sc.nextLine().trim());
	                switch (choice) {
	                    case 1:
	                        System.out.print("Enter new opening date (M/D/YYYY): ");
	                        LocalDate openingDate = LocalDate.parse(sc.nextLine().trim(), DATE_FORMATTER);
	                        System.out.print("Enter new closing date (M/D/YYYY): ");
	                        LocalDate closingDate = LocalDate.parse(sc.nextLine().trim(), DATE_FORMATTER);
	                        
	                        proj.setOpeningDate(openingDate);
	                        proj.setClosingDate(closingDate);
	                        break;
	
	                    case 2:
	                        System.out.print("Enter new number of officer slots (max 10): ");
	                        int slots = Math.min(10, Integer.parseInt(sc.nextLine().trim()));
	                        proj.setOfficerSlots(slots);
	                        break;
	                    	
	                    case 3:
	                        return;
	
	                    default:
	                        System.out.println("Invalid choice.");
	                }
	            } catch (Exception e) {
	                System.out.println("Error: " + e.getMessage());
	            }
			}
                	
		} else {
			System.out.println("Project not found.");
		}
		
	}
	
	public void deleteProject(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the project name to delete: ");
		String name = sc.nextLine().trim();
		
		Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
		if (myProjects.containsKey(name)) {
			ProjectController.removeProject(name);
			System.out.println("Deleted.");
		} else {
			System.out.println("Project not found.");
		}
		
	}
	
	public void viewAllProjects(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		
		Map<String, Project> allProjects = ProjectController.managerViewAllProjects();
		
		if (manager.getWantSort() == 1) {
			allProjects = new TreeMap<>(allProjects);
		}
		
		for(Map.Entry<String, Project> entry : allProjects.entrySet()) {
			if (manager.getNeighbourhoodFilter().isEmpty() || entry.getValue().getNeighborhood().equalsIgnoreCase(manager.getNeighbourhoodFilter())) {
		        System.out.printf("Project Name: %s\n", entry.getValue().getProjectName());
		        System.out.printf("Neighbourhood: %s\n", entry.getValue().getNeighborhood());
		            
		        if (manager.getTypeFilter().isEmpty()) {
		        	System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getTotalUnits());
			        System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getAvailableUnits());
			        System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getTotalUnits());
			        System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getAvailableUnits());
		         } else if (manager.getTypeFilter().equalsIgnoreCase("2-Room")) {
		        	 System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getTotalUnits());
			         System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getAvailableUnits());
		         } else if (manager.getTypeFilter().equalsIgnoreCase("3-Room")) {
		            System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getTotalUnits());
			        System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getAvailableUnits());
		         }
		            
		        System.out.printf("Opening Date: %s\n", entry.getValue().getOpeningDate());
		        System.out.printf("Closing Date: %s\n", entry.getValue().getClosingDate());
		        System.out.printf("HDB Manager: %s\n", entry.getValue().getAssignedManager().getName());
		        System.out.printf("Available Officer Slots: %s\n", entry.getValue().getOfficerSlots());
		        System.out.println("------");
			}
		}
    }
	
	public void viewMyProjects(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		
		Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
		
		if (myProjects.isEmpty()) {
			System.out.println("No projects under your management.");
			return;
		}
		if (manager.getWantSort() == 1) {
			myProjects = new TreeMap<>(myProjects);
		}
		
		for(Map.Entry<String, Project> entry : myProjects.entrySet()) {
			if (manager.getNeighbourhoodFilter().isEmpty() || entry.getValue().getNeighborhood().equalsIgnoreCase(manager.getNeighbourhoodFilter())) {
		        System.out.printf("Project Name: %s\n", entry.getValue().getProjectName());
		        System.out.printf("Neighbourhood: %s\n", entry.getValue().getNeighborhood());
		            
		        if (manager.getTypeFilter().isEmpty()) {
		        	System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getTotalUnits());
			        System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getAvailableUnits());
			        System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getTotalUnits());
			        System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getAvailableUnits());
		         } else if (manager.getTypeFilter().equalsIgnoreCase("2-Room")) {
		        	 System.out.printf("Total Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getTotalUnits());
			         System.out.printf("Available Number of 2-Room Flats: %d\n", entry.getValue().getUnitTypes().get("2-Room").getAvailableUnits());
		         } else if (manager.getTypeFilter().equalsIgnoreCase("3-Room")) {
		            System.out.printf("Total Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getTotalUnits());
			        System.out.printf("Available Number of 3-Room Flats: %d\n", entry.getValue().getUnitTypes().get("3-Room").getAvailableUnits());
		         }
		            
		        System.out.printf("Opening Date: %s\n", entry.getValue().getOpeningDate());
		        System.out.printf("Closing Date: %s\n", entry.getValue().getClosingDate());
		        System.out.printf("HDB Manager: %s\n", entry.getValue().getAssignedManager().getName());
		        System.out.println("------");
			}
		}
	}
	
	public void togglesVisibility(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
		
		
		for(Map.Entry<String, Project> entry : myProjects.entrySet()) {
			System.out.printf("%s (Currently: %s)\n", entry.getKey(), entry.getValue().isVisible() ? "Visible" : "Hidden");
		}
		
		Scanner sc = new Scanner(System.in);
        System.out.print("Enter Project Name to toggle visibility: ");
        String name = sc.nextLine().trim();
        
        if (myProjects.containsKey(name)) {
        	boolean v = myProjects.get(name).isVisible();
        	myProjects.get(name).setVisible(!v);
        } else {
        	System.out.println("Project not found.");
        }
	}
	
	public void viewOfficerRegistrations(Manager manager) {
		IRegistrationServices RegistrationController = new RegistrationController();
		// get registrations for his projects
		Map<Integer, Registration> registrations = RegistrationController.ManagerGetProjectRegistration(manager);
		
		if (registrations.size() == 0) {
			System.out.println("No Officer Registration.");
		}
		
		for(Map.Entry<Integer, Registration> entry : registrations.entrySet()) {
            System.out.printf("\nRegistration ID: %s\n", entry.getKey());
            System.out.printf("Officer NRIC: %s\n", entry.getValue().getOfficer().getNric());
            System.out.printf("Project: %s\n", entry.getValue().getProject().getProjectName());
            System.out.printf("Status: %s\n", entry.getValue().getStatus());
            System.out.print("--------\n");
        }
    }
        
	
	public void processOfficerRegistration(Manager manager) {
		Scanner sc = new Scanner(System.in);
		IRegistrationServices regSvc = new RegistrationController();
		// 1) Fetch all registrations for this manager and keep only pending
		Map<Integer, Registration> regs = regSvc.ManagerGetProjectRegistration(manager);
		regs.values().removeIf(r -> r.getStatus() != RegistrationStatus.PENDING);
	
		// 2) If none pending, return
		if (regs.isEmpty()) {
			System.out.println("No pending officer registrations.");
			return;
		}
	
		// 3) Display each pending registration for review
		System.out.println("Pending Officer Registrations:");
		for (Map.Entry<Integer, Registration> entry : regs.entrySet()) {
			Registration r = entry.getValue();
			System.out.printf("Registration ID: %d%n", entry.getKey());
			System.out.printf("Officer NRIC: %s%n", r.getOfficer().getNric());
			System.out.printf("Officer Name: %s%n", r.getOfficer().getName());
			System.out.printf("Project: %s%n", r.getProject().getProjectName());
			System.out.printf("Status: %s%n", r.getStatus());
			System.out.println("----");
		}
	
		// 4) Prompt manager to choose one to process
		System.out.print("Enter the Registration ID to process: ");
		String line = sc.nextLine().trim();
		int id;
		try {
			id = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid registration ID.");
			return;
		}
	
		if (!regs.containsKey(id)) {
			System.out.println("Registration ID not found among pendings.");
			return;
		}
	
		Registration reg = regs.get(id);
	
		// 5) Check slot availability
		if (reg.getProject().getOfficerSlots() == 0) {
			System.out.println("Officer slots full. Cannot approve.");
			reg.setStatus(RegistrationStatus.UNSUCCESSFUL);
			return;
		}
	
		// 6) Prompt for decision
		System.out.print("Approve registration? (Y/N): ");
		String choice = sc.nextLine().trim();
		if (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
			System.out.println("Please enter Y or N.");
			return;
		}
	
		if (choice.equalsIgnoreCase("Y")) {
			// Approve
			reg.setStatus(RegistrationStatus.SUCCESSFUL);
			System.out.println("Registration approved.");
	
			// Add to officer's profile
			Officer officer = reg.getOfficer();
			officer.addHandledProject(
				reg.getProject().getProjectName(),
				reg.getProject()
			);
	
			// Decrement project officer slots
			Project proj = reg.getProject();
			proj.setOfficerSlots(proj.getOfficerSlots() - 1);
	
			// Reject overlapping pending registrations
			Map<Integer, Registration> officerRegs = regSvc.OfficerGetOwnRegistration(officer);
			LocalDate start = proj.getOpeningDate();
			LocalDate end = proj.getClosingDate();
			for (Registration other : officerRegs.values()) {
				if (other.getStatus() == RegistrationStatus.PENDING) {
					LocalDate oStart = other.getProject().getOpeningDate();
					LocalDate oEnd = other.getProject().getClosingDate();
					if (!start.isAfter(oEnd) && !oStart.isAfter(end)) {
						other.setStatus(RegistrationStatus.UNSUCCESSFUL);
					}
				}
			}
		} else {
			// Reject
			reg.setStatus(RegistrationStatus.UNSUCCESSFUL);
			System.out.println("Registration rejected.");
		}
	}
		
	
	public void processBTOApplications(Manager manager) {
		Scanner sc = new Scanner(System.in);
		IProjectServices projectSvc = new ProjectController();
		IApplicationServices appSvc   = new ApplicationController();
	
		// 1) Gather pending apps for this manager
		Map<String, Project> myProjects = projectSvc.managerGetProjects(manager);
		Map<Integer, Application> pending =
			appSvc.ManagerGetPendingApplications(myProjects);
	
		if (pending.isEmpty()) {
			System.out.println("No pending BTO applications to process.");
			return;
		}
	
		// 2) Display them
		System.out.println("Pending Applications:");
		for (Map.Entry<Integer, Application> e : pending.entrySet()) {
			Application a = e.getValue();
			System.out.printf("  ID: %d | Applicant: %s | Project: %s | Flat: %s\n",
				e.getKey(),
				a.getApplicant().getName(),
				a.getProject().getProjectName(),
				a.getUnitType()
			);
		}
	
		// 3) Pick one
		System.out.print("Enter application ID to process: ");
		int id;
		try {
			id = Integer.parseInt(sc.nextLine().trim());
		} catch (NumberFormatException ex) {
			System.out.println("Invalid ID. Returning to menu.");
			return;
		}
	
		if (!pending.containsKey(id)) {
			System.out.println("ID not found among pending applications.");
			return;
		}
	
		Application app = pending.get(id);
		String unitType = app.getUnitType();
		UnitType ut = app.getProject().getUnitTypes().get(unitType);
		
		// automatically reject when full
		if (app.getProject().getUnitTypes().get(unitType).getAvailableUnits() == 0) {
			app.setStatus(ApplicationStatus.UNSUCCESSFUL);
			System.out.println("Application rejected. Project full.");
			return;
		}
		
	
		// 4) Approve or reject
		System.out.print("Approve application? (Y/N): ");
		String choice = sc.nextLine().trim();
		if (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
			System.out.println("Please enter Y or N. Aborting.");
			return;
		}
	
		if (choice.equalsIgnoreCase("Y")) {
			// Approve → SUCCESSFUL
			app.setStatus(ApplicationStatus.SUCCESSFUL);
			System.out.println("Application approved.");
			app.getProject().getUnitTypes().get(unitType).decrementAvailableUnits();
	
		} else {
			// Reject → UNSUCCESSFUL
			app.setStatus(ApplicationStatus.UNSUCCESSFUL);
			System.out.println("Application rejected.");
		}
	}
	
	public void processBTOWithdrawals(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		IApplicationServices ApplicationController = new ApplicationController();
		
		Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
		
		Map<Integer, Application> applications = ApplicationController.ManagerGetCancellingApplications(myProjects);
		
		// display
		for (Map.Entry<Integer, Application> entry : applications.entrySet()) {
			System.out.printf("Application ID: %d\n", entry.getKey());
			System.out.printf("Applicant: %s\n", entry.getValue().getApplicant().getName());
			System.out.printf("Project: %s\n", entry.getValue().getProject().getProjectName());
			System.out.printf("Status: %s\n", entry.getValue().getStatus());
			System.out.println("----");
		}
		
		// approve or reject
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter application ID: ");
		try {
			int id = Integer.parseInt(sc.nextLine().trim());
			if (applications.containsKey(id)) {
				Project p = applications.get(id).getProject();
				String uType = applications.get(id).getUnitType();
				Application app = applications.get(id);
						
				// get yes or no
				System.out.print("Y for approve, N for reject: ");
				String approval = sc.nextLine().trim();				
				if (!approval.equalsIgnoreCase("Y") && !approval.equalsIgnoreCase("N")) {
	            	System.out.println("Please enter Y or N.");
	            	return;
	            }
				
				if (approval.equalsIgnoreCase("Y")) {
					if (app.getStatus() == ApplicationStatus.PENDING_CANCEL) {
						// increment availableUnits
						app.getProject().getUnitTypes().get(uType).incrementAvailableUnits();
					} else {
						app.getProject().getUnitTypes().get(uType).incrementAvailableUnits();
						app.getProject().getUnitTypes().get(uType).unassignUnit();
						app.getApplicant().setflatTypeBooked("");
					}
							
					app.setStatus(ApplicationStatus.UNSUCCESSFUL);
				} else {
					app.setStatus(ApplicationStatus.PENDING);
				}
						
			} else {
				System.out.println("ID not found.");
			}
					
		} catch (NumberFormatException e) {
			System.out.println("Please enter valid numbers.");
		}
	}
	
	public void generateReport(Manager manager) {
		// ask for filters
		Scanner sc = new Scanner(System.in);
		try {	
			System.out.println("Enter Flat type, (1) for 2-Room, (2) for 3-Room, (0) for both: ");
			int fType = Integer.parseInt(sc.nextLine().trim());
			if (fType<0 || fType>2) {
				System.out.println("Please enter a valid option.");
				return;
			}
			System.out.println("Enter Project Name to filter. (Empty for no filter): ");
			String pName = sc.nextLine().trim();
			System.out.println("Enter age: (0 for no filter)");
			int age = Integer.parseInt(sc.nextLine().trim());
			System.out.println("Enter Marital Status: (Empty for no filter)");
			String mStatus = sc.nextLine().trim();
			
			// retrieve booked applications for my projects
			IProjectServices ProjectController = new ProjectController();
			IApplicationServices ApplicationController = new ApplicationController();
			
			Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
			Map<Integer, Application> applications = ApplicationController.getProjectsBookedApplications(myProjects);
			
			if (applications.isEmpty()) {
				System.out.println("No bookings have been made.");
				return;
			}
			// filter	
			for(Map.Entry<Integer, Application> entry : applications.entrySet()) {
				if (fType == 0 || (fType == 1 && entry.getValue().getUnitType() == "2-Room") || (fType == 2 && entry.getValue().getUnitType() == "3-Room")) {
					if (pName.isEmpty() || pName.equalsIgnoreCase(entry.getValue().getProject().getProjectName())) {
						if (age == 0 || age == entry.getValue().getApplicant().getAge()) {
							if (mStatus.isEmpty() || mStatus.equalsIgnoreCase(entry.getValue().getApplicant().getMaritalStatus())) {
								System.out.printf("Applicant age: %d\n", entry.getValue().getApplicant().getAge());
					    		System.out.printf("Applicant Marital Status: %s\n", entry.getValue().getApplicant().getMaritalStatus());
					    		System.out.printf("Flat Type Booked: %s\n", entry.getValue().getUnitType()); 		
					    		System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());
					            System.out.println("------");
							}
						}
					}
				}	
			}
					
		} catch (NumberFormatException e) {
			System.out.println("Please enter valid numbers.");
		}	
	}
	
	public void viewAllEnquiries(Manager manager) {
		IEnquiryServices EnquiryController = new EnquiryController();
		
		Map<Integer, Enquiry> enquiries = EnquiryController.ManagerGetAllEnquiries();
		
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
	
	public void replyToEnquiries(Manager manager) {
		IProjectServices ProjectController = new ProjectController();
		IEnquiryServices EnquiryController = new EnquiryController();
		
		Map<String, Project> myProjects = ProjectController.managerGetProjects(manager);
		Map<Integer, Enquiry> enquiries = EnquiryController.getProjectsEnquiries(myProjects);
		
		// display enquiries to my projects
		for(Map.Entry<Integer, Enquiry> entry : enquiries.entrySet()) {
    		System.out.printf("Enquiry ID: %d\n", entry.getKey());
    		System.out.printf("Project Name: %s\n", entry.getValue().getProject().getProjectName());
    		System.out.printf("Question: %s\n", entry.getValue().getQuestion());
    		System.out.printf("Reply: %s\n", entry.getValue().getReply());
    		System.out.println("------");
    	}
		
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter the enquiry ID you wish to reply: ");
			int id = Integer.parseInt(sc.nextLine().trim());
			if (enquiries.containsKey(id)) {
    			System.out.println("Enter response: ");
    			String reply = sc.nextLine().trim();
    			enquiries.get(id).setReply(reply);
    			System.out.println("Enquiry replied.");
    		} else {
    			System.out.println("Enquiry ID not found.");
    		}
			
			
		} catch (NumberFormatException e) {
			System.out.println("Please enter valid numbers.");
		}
		
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
