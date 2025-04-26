package model;

import java.time.LocalDateTime;

/**
 * Represents a flat booking application submitted by an Applicant.
 * <p>
 * Tracks the applicant, project, chosen unit type, application status,
 * and timestamp of submission.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Application {

    /**
     * Unique identifier for this application.
     */
    private int applicationId;

    /**
     * The Applicant who submitted this application.
     */
    private Applicant applicant;

    /**
     * The Project for which the application is submitted.
     */
    private Project project;

    /**
     * The type of flat chosen by the applicant.
     */
    private String unitType;

    /**
     * Current status of the application (e.g., PENDING, BOOKED).
     */
    private ApplicationStatus status;

    /**
     * Timestamp when the application was created.
     */
    private LocalDateTime applicationDate;

    /**
     * Constructs a new Application with PENDING status and current timestamp.
     *
     * @param appl     the Applicant submitting the application
     * @param proj     the Project for which the application is made
     * @param unitType the chosen flat unit type
     */
    public Application(Applicant appl, Project proj, String unitType) {
        this.applicant = appl;
        this.project = proj;
        this.unitType = unitType;
        this.status = ApplicationStatus.PENDING;
        this.applicationDate = LocalDateTime.now();
    }

    // Getters

    /**
     * Returns the unique ID of this application.
     *
     * @return applicationId
     */
    public int getApplicationId() {
        return applicationId;
    }

    /**
     * Returns the Applicant who made this application.
     *
     * @return applicant
     */
    public Applicant getApplicant() {
        return this.applicant;
    }

    /**
     * Returns the Project associated with this application.
     *
     * @return project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Returns the chosen unit type of this application.
     *
     * @return unitType
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * Returns the current status of this application.
     *
     * @return status
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Returns the date and time when the application was submitted.
     *
     * @return applicationDate
     */
    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    // Setters

    /**
     * Updates the status of this application.
     *
     * @param status the new ApplicationStatus
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    /**
     * Sets the unique identifier for this application.
     *
     * @param id the application ID to assign
     */
    public void setID(int id) {
    	this.applicationId = id;
    }

    /**
     * Returns a formatted string representing this application.
     *
     * @return formatted application details
     */
    @Override
    public String toString() {
        return String.format("Application %s: %s - %s (%s)",
                applicationId, project.getProjectName(), unitType, status);
    }
}

