package Interface;

import java.util.Map;
import model.Applicant;
import model.Application;
import model.Officer;
import model.Project;

/**
 * Defines application‐management services for various roles:
 * <ul>
 *   <li>Adding and removing applications</li>
 *   <li>Retrieving successful applications for officers</li>
 *   <li>Retrieving an applicant’s own applications</li>
 *   <li>Retrieving booked, pending, and cancelling applications for managers</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IApplicationServices {

	/**
     * Adds a new application to the system.
     *
     * @param app the Application object to add
     */
	public void addApplication(Application app);

	/**
     * Removes an existing application from the system.
     *
     * @param id the ID of the application to remove
     */
	public void removeApplication(int id);

	/**
     * Retrieves all successfully booked applications assigned to a specific officer.
     *
     * @param officer the Officer whose successful applications to retrieve
     * @return map of application IDs to Application objects that are booked for that officer
     */
	public Map<Integer, Application> officerGetSuccessfulApplication(Officer officer);

	/**
     * Retrieves all applications submitted by a specific applicant.
     *
     * @param applicant the Applicant whose applications to fetch
     * @return map of application IDs to Application objects belonging to that applicant
     */
	public Map<Integer, Application> ApplicantGetOwnApplication(Applicant applicant);

	/**
     * Retrieves all applications with status BOOKED for the given set of projects.
     *
     * @param projects map of project names to Project objects
     * @return map of application IDs to booked Application objects in those projects
     */
	public Map<Integer, Application> getProjectsBookedApplications(Map<String, Project> projects);

	/**
     * Retrieves all pending applications across a manager’s projects.
     *
     * @param projects map of project names to Project objects managed by the manager
     * @return map of application IDs to Application objects with status PENDING
     */
	public Map<Integer, Application> ManagerGetPendingApplications(Map<String, Project> projects);

	/**
     * Retrieves all applications currently in cancellation process for a manager’s projects.
     *
     * @param projects map of project names to Project objects managed by the manager
     * @return map of application IDs to Application objects with status PENDING_CANCEL or PENDING_CANCEL_BOOKING
     */
	public Map<Integer, Application> ManagerGetCancellingApplications(Map<String, Project> projects);
}
