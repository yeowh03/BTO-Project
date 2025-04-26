package model;

/**
 * Enumerates the types of users in the system and their roles:
 * <ul>
 *   <li>{@link #APPLICANT} – submits flat applications and enquiries</li>
 *   <li>{@link #OFFICER} – processes applications, handles enquiries, and registers for projects</li>
 *   <li>{@link #MANAGER} – creates projects, approves applications, and manages registrations</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public enum UserType {
    /** A user who applies for flats and submits enquiries. */
    APPLICANT,

    /** A user who processes applications and handles enquiries. */
    OFFICER,

    /** A user who creates projects and manages approvals. */
    MANAGER
}