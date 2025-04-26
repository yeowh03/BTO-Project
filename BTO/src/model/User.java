package model;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Base class representing a system user with common attributes and filter settings.
 * <p>
 * Stores personal details and provides utility to update project browsing filters.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class User {

    /** The user’s name. */
    private String name;

    /** The user’s NRIC (unique identifier). */
    private String nric;

    /** The user’s age in years. */
    private int age;

    /** The user’s marital status ("Single" or "Married"). */
    private String maritalStatus;

    /** The user’s login password. */
    private String password;
    
    /** Neighborhood filter for browsing projects. */
    private String neighbourhoodFilter = "";

    /** Flat-type filter for browsing projects. */
	private String typeFilter = "";

    /** Sorting choice: 1 = sort by project name, 0 = no sorting. */
	private int wantSort = 1;
	
    /**
     * Constructs a new User with specified personal details.
     *
     * @param name           the user’s name
     * @param nric           the user’s NRIC (unique)
     * @param age            the user’s age
     * @param maritalStatus  the user’s marital status
     * @param password       the user’s password
     */
    public User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    // Getters

    /**
     * Returns the user’s name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user’s NRIC.
     *
     * @return nric
     */
    public String getNric() {
        return nric;
    }

    /**
     * Returns the user’s age.
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the user’s marital status.
     *
     * @return maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }
    
    /**
     * Returns the user’s password.
     *
     * @return password
     */
    public String getPassword() {
    	return password;
    }
    
    /**
     * Returns the current neighborhood filter.
     *
     * @return neighbourhoodFilter
     */
    public String getNeighbourhoodFilter() {
    	return neighbourhoodFilter;
    }

    /**
     * Returns the current flat type filter.
     *
     * @return typeFilter
     */
    public String getTypeFilter() {
    	return typeFilter;
    }

    /**
     * Returns sortting requirements
     *
     * @return wantSort
     */
    public int getWantSort() {
    	return wantSort;
    }

    // Setters

    /**
     * Updates the user’s full name.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the user’s age.
     *
     * @param age the new age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Updates the user’s marital status.
     *
     * @param maritalStatus the new marital status to set
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    
    /**
     * Prompts the console user to enter and set a new password.
     */
    public void setPassword() {
		System.out.println("Enter new password: ");
		Scanner scanner = new Scanner(System.in);
		String newPassword = scanner.nextLine().trim();
		this.password = newPassword;
	}
    
    /**
     * Prompts for and updates the neighborhood filter,
     * flat‐type filter, and sorting preference.
     * <p>
     * Continues to prompt until a valid sort choice (0 or 1) is entered.
     * </p>
     */
    public void updateFilter() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter neighbourhood: ");
        neighbourhoodFilter = sc.nextLine().trim();
        System.out.println("Enter type: ");
        typeFilter = sc.nextLine().trim();
        do {
        	try {
        		System.out.println("Enter 1 for sorting, 0 for no sorting by Project Name: ");
	        	wantSort = sc.nextInt();
	        	if (wantSort != 0 && wantSort != 1) {
	        		 System.out.println("Please enter 0 or 1.");
	        	}
        	} catch (InputMismatchException e){
        		 System.out.println("Invalid input.");
        		 sc.next();
        	}
        }while (wantSort != 0 && wantSort != 1);
	}
}
