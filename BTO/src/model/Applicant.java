package model;

/**
 * Represents an HDB BTO applicant, extending the generic User class.
 * <p>
 * Tracks the flat type that the applicant has successfully booked.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Applicant extends User{
	
	/**
     * The type of flat this applicant has booked (e.g., "3-Room").
     * Empty if no booking has been made.
     */
	private String flatTypeBooked = "";
	
	/**
     * Constructs a new Applicant with the given personal details.
     *
     * @param name           the applicant’s name
     * @param nric           the applicant’s NRIC (unique identifier)
     * @param age            the applicant’s age in years
     * @param maritalStatus  "Single" or "Married"
     * @param password       the applicant’s login password
     */
	public Applicant(String name, String nric, int age, String maritalStatus, String password) {
		super(name, nric, age, maritalStatus, password);
	}
	
	    /**
     * Retrieves and prints the flat type that has been booked.
     * <p>
     * If no flat has been booked yet, prints a notice.
     * </p>
     *
     * @return the booked flat type, or an empty string if none
     */
	public String getflatTypeBooked() {
	if (flatTypeBooked == null || flatTypeBooked.isEmpty()) {
		System.out.println("No flat has been booked.");
	} else {
    System.out.println(flatTypeBooked);
}
		return flatTypeBooked;
	}
	
	/**
     * Sets the flat type that this applicant has booked.
     *
     * @param newFlatType the flat type to record as booked
     */
	public void setflatTypeBooked(String newFlatType) {
		this.flatTypeBooked = newFlatType;
	}
	
}
