package menu;

import model.Applicant;
import controller.ApplicantController;
import controller.EnquiryController;
import java.util.Scanner;

import Interface.IApplicantServices;

public class ApplicantMenu {
    private Scanner scanner;
    private Applicant applicant;

    public ApplicantMenu(Applicant applicant) {
        this.scanner = new Scanner(System.in);
        this.applicant = applicant;
    }

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
