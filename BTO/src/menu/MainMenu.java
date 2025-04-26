package menu;

import auth.AuthenticationSystem;
import java.util.Scanner;
import model.Applicant;
import model.Manager;
import model.Officer;
import model.UserType;

/**
 * Main entry‐point menu for the BTO Management System.
 * <p>
 * Users can:
 * <ul>
 *   <li>Login as Applicant, Officer, or Manager</li>
 *   <li>Access role‐specific menus</li>
 *   <li>Exit the application</li>
 * </ul>
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class MainMenu {

    /** Scanner for reading console input. */
    private Scanner scanner;

    /** AuthenticationSystem used to validate user credentials. */
    private AuthenticationSystem authSystem;

    /**
     * Constructs the MainMenu and initializes input scanner and auth system.
     */
    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.authSystem = new AuthenticationSystem();
    }

    /**
     * Displays the main menu loop: login or exit.
     */
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== BTO Management System ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        showLoginMenu();
                        break;
                    case 2:
                        System.out.println("Thank you for using BTO Management System");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Displays the login submenu for selecting user type.
     */
    private void showLoginMenu() {
        while (true) {
            System.out.println("\n=== Login Menu ===");
            System.out.println("1. Applicant Login");
            System.out.println("2. Officer Login");
            System.out.println("3. Manager Login");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        handleApplicantLogin();
                        break;
                    case 2:
                        handleOfficerLogin();
                        break;
                    case 3:
                        handleManagerLogin();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts for applicant credentials and launches the ApplicantMenu on success.
     */
    private void handleApplicantLogin() {
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Applicant applicant = (Applicant) authSystem.login(nric, password, UserType.APPLICANT);
        if (applicant != null) {
            new ApplicantMenu(applicant).display();
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    /**
     * Prompts for officer credentials and launches the OfficerMenu on success.
     */
    private void handleOfficerLogin() {
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Officer officer = (Officer) authSystem.login(nric, password, UserType.OFFICER);
        if (officer != null) {
            new OfficerMenu(officer).display();
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    /**
     * Prompts for manager credentials and launches the ManagerMenu on success.
     */
    private void handleManagerLogin() {
        System.out.print("Enter NRIC: ");
        String nric = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Manager manager = (Manager) authSystem.login(nric, password, UserType.MANAGER);
        if (manager != null) {
            new ManagerMenu(manager).display();
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

}


