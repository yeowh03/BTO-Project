package menu;

import Interface.IApplicantServices;
import controller.ApplicantController;
import java.util.Scanner;
import model.Applicant;

/**
 * Provides the console‐based menu for Applicants to interact with the system.
 * <p>
 * Options include:
 * <ul>
 *   <li>Browse projects</li>
 *   <li>Submit application</li>
 *   <li>View application status</li>
 *   <li>Submit, view, edit, delete enquiries</li>
 *   <li>Request withdrawal</li>
 *   <li>Logout</li>
 * </ul>
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class ApplicantMenu {

    /** Scanner for reading console input. */
    private Scanner scanner;

    /** The currently logged-in Applicant. */
    private Applicant applicant;


    /**
     * Constructs the ApplicantMenu for a given applicant.
     *
     * @param applicant the Applicant for whom the menu is displayed
     */
    public ApplicantMenu(Applicant applicant) {
        
        this.scanner = new Scanner(System.in);
        this.applicant = applicant;
    }

    /**
     * Displays the menu loop, prompts the user for choices,
     * and delegates to the appropriate ApplicantController or EnquiryController methods.
     */
    public void display() {
        // Managers cannot access BTO application features
    	IApplicantServices controller = new ApplicantController();
        while (true) {
            System.out.println("\n=== Applicant Menu ===");
            System.out.println("Welcome, " + applicant.getName());
            System.out.println("1. Browse Projects");
            System.out.println("2. Submit Application");
            System.out.println("3. View Application Status");
            System.out.println("4. Submit New Enquiry");
            System.out.println("5. View My Enquiries");
            System.out.println("6. Edit Enquiry");
            System.out.println("7. Delete Enquiry");
            System.out.println("8. Request Withdrawal");
            System.out.println("9. Change Password");
            System.out.println("10. View Flat Booked");
            System.out.println("11. Update Filters");
            System.out.println("12. Logout");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                    	controller.browseProjects(applicant);
                        break;
                    case 2:
                    	controller.submitApplication(applicant);
                        break;
                    case 3:
                    	controller.viewApplicationStatus(applicant);
                        break;
                    case 4:
                    	controller.submitEnquiry(applicant);
                        break;
                    case 5:
                    	controller.viewEnquiries(applicant);
                        break;
                    case 6:
                    	controller.editEnquiry(applicant);
                        break;
                    case 7:
                    	controller.deleteEnquiry(applicant);
                        break;
                    case 8:
                    	controller.requestWithdrawal(applicant);
                        break;
                    case 9:
                    	applicant.setPassword();
                        break;
                    case 10:
                    	applicant.getflatTypeBooked();
                        break;
                    case 11:
                    	applicant.updateFilter();
                        break;
                    case 12:
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
