package model;

/**
 * Represents a registration request by an Officer for a BTO project.
 * <p>
 * Tracks the project, officer, current status, and unique ID.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Registration {

	/** Unique identifier for this registration. */
	private int id;

	/** The Project to which the officer is registering. */
	private Project project;

	/** The Officer making the registration. */
	private Officer officer;

	/** Current status of the registration (e.g., PENDING, APPROVED). */
	private RegistrationStatus status;
	
	/**
     * Constructs a new Registration with PENDING status.
     *
     * @param project the Project being registered for
     * @param officer the Officer submitting the registration
     */
	public Registration(Project project, Officer officer) {
		this.project = project;
		this.officer = officer;
		this.status = RegistrationStatus.PENDING;
	}
	
	/**
     * Returns the project associated with this registration.
     *
     * @return the Project object
     */
	public Project getProject() {
		return this.project;
	}
	
	/**
     * Returns the officer who made this registration.
     *
     * @return the Officer object
     */
	public Officer getOfficer() {
		return this.officer;
	}
	
	/**
     * Returns the current status of this registration.
     *
     * @return the RegistrationStatus value
     */
	public RegistrationStatus getStatus() {
		return this.status;
	}
	
	/**
     * Updates the project for this registration.
     *
     * @param project the new Project to set
     */
	public void setProject(Project project) {
		this.project = project;
	}
	
	/**
     * Updates the officer for this registration.
     *
     * @param officer the new Officer to set
     */
	public void setOfficer(Officer officer) {
		this.officer = officer;
	}
	
	/**
     * Updates the status of this registration.
     *
     * @param status the new RegistrationStatus to set
     */
	public void setStatus(RegistrationStatus status) {
		this.status = status;
	}
	
	/**
     * Assigns the unique identifier for this registration.
     *
     * @param id the registration ID to set
     */
	public void setID(int id) {
		this.id = id;
	}
}

