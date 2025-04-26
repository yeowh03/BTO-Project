package model;

/**
 * Represents a manager user in the BTO system.
 * <p>
 * Inherits common user attributes (name, NRIC, age, marital status, password)
 * and can be extended with manager‐specific behaviors if needed.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Manager extends User{
	
	/**
     * Constructs a new Manager with the given personal details.
     *
     * @param name           the manager’s name
     * @param nric           the manager’s NRIC (unique identifier)
     * @param age            the manager’s age
     * @param maritalStatus  the manager’s marital status ("Single" or "Married")
     * @param password       the manager’s login password
     */
	public Manager(String name, String nric, int age, String maritalStatus, String password){
		super(name, nric, age, maritalStatus, password);
	}
}
