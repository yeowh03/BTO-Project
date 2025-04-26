package menu;

import Interface.IManagerServices;
import controller.ManagerController;
import java.time.format.DateTimeFormatter;
import java.util.*;
import model.Manager;

/**
 * Console‐based menu for Manager users to interact with the system.
 * <p>
 * Options include:
 * <ul>
 *   <li>Create, edit, delete BTO projects</li>
 *   <li>View all projects or only own projects</li>
 *   <li>Toggle project visibility</li>
 *   <li>Handle officer registrations</li>
 *   <li>Process BTO applications and withdrawals</li>
 *   <li>Generate summary reports</li>
 *   <li>View and reply to enquiries</li>
 *   <li>Update filters, change password, logout</li>
 * </ul>
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class ManagerMenu {

    /** Scanner for reading console input. */
    private Scanner scanner;

    /** The currently logged‐in Manager. */
    private Manager manager;

    /** Formatter for parsing and printing dates (M/d/yyyy). */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    /**
     * Constructs the ManagerMenu for a given manager.
     *
     * @param manager the Manager for whom the menu is displayed
     */
    public ManagerMenu(Manager manager) {
        this.scanner = new Scanner(System.in);
        this.manager = manager;
    }

    /**
     * Displays the menu loop, prompts for choices, and
     * delegates actions to ManagerController or model methods.
     */
    public void display() {
        while (true) {
            System.out.println("\n=== HDB Manager Menu ===");
            System.out.println("Welcome, " + manager.getName());
            System.out.println("\n=== Project Management ===");
            System.out.println("1. Create New Project");
            System.out.println("2. Edit Project");
            System.out.println("3. Delete Project");
            System.out.println("4. View All Projects");
            System.out.println("5. View My Projects");
            System.out.println("6. Toggle Project Visibility");

            System.out.println("\n=== Officer Management ===");
            System.out.println("7. View Officer Registrations");
            System.out.println("8. Process Officer Registration");

            System.out.println("\n=== Application Management ===");
            System.out.println("9. Process BTO Applications");
            System.out.println("10. Process Withdrawal Requests");

            System.out.println("\n=== Reports & Enquiries ===");
            System.out.println("11. Generate Reports");
            System.out.println("12. View All Enquiries");
            System.out.println("13. Reply to Project Enquiries");

            System.out.println("\n=== General ===");
            System.out.println("14. Update filters");
            System.out.println("15. Change Password");
            System.out.println("16. Logout");

            System.out.print("\nEnter your choice: ");

            try {
            	IManagerServices ManagerController = new ManagerController();
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                    	ManagerController.createProject(manager);
                        break;
                    case 2:
                    	ManagerController.editProject(manager);
                        break;
                    case 3:
                    	ManagerController.deleteProject(manager);
                        break;
                    case 4:
                    	ManagerController.viewAllProjects(manager);
                        break;
                    case 5:
                    	ManagerController.viewMyProjects(manager);
                        break;
                    case 6:
                    	ManagerController.togglesVisibility(manager);
                        break;
                    case 7:
                    	ManagerController.viewOfficerRegistrations(manager);
                        break;
                    case 8:
                    	ManagerController.processOfficerRegistration(manager);
                        break;
                    case 9:
                    	ManagerController.processBTOApplications(manager);
                        break;
                    case 10:
                    	ManagerController.processBTOWithdrawals(manager);
                        break;
                    case 11:
                    	ManagerController.generateReport(manager);
                        break;
                    case 12:
                    	ManagerController.viewAllEnquiries(manager);
                        break;
                    case 13:
                    	ManagerController.replyToEnquiries(manager);
                        break;
                    case 14:
                        manager.updateFilter();
                        break;
                    case 15:
                        manager.setPassword();
                        break;
                    case 16:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

   

}
