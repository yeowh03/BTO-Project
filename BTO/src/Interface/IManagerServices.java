package Interface;

import model.Manager;

/**
 * Defines management services available to Manager users:
 * <ul>
 *   <li>Create, edit, delete BTO projects</li>
 *   <li>View all projects or own projects</li>
 *   <li>Toggle project visibility</li>
 *   <li>View and process officer registrations</li>
 *   <li>Process applicant applications and withdrawals</li>
 *   <li>Generate reports</li>
 *   <li>View and reply to enquiries</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IManagerServices {

	/**
     * Creates a new BTO project.
     *
     * @param manager the Manager creating the project
     */
	public void createProject(Manager manager);

	/**
     * Edits details of an existing project.
     *
     * @param manager the Manager editing the project
     */
	public void editProject(Manager manager);

	/**
     * Deletes a project managed by the manager.
     *
     * @param manager the Manager deleting the project
     */
	public void deleteProject(Manager manager);

	/**
     * Displays all BTO projects in the system.
     *
     * @param manager the Manager viewing all projects
     */
	public void viewAllProjects(Manager manager);

	/**
     * Displays only the projects created by this manager.
     *
     * @param manager the Manager viewing own projects
     */
	public void viewMyProjects(Manager manager);

	/**
     * Toggles the visibility flag of a selected project.
     *
     * @param manager the Manager toggling project visibility
     */
	public void togglesVisibility(Manager manager);

	/**
     * Retrieves all pending officer registration requests.
     *
     * @param manager the Manager viewing registrations
     */
	public void viewOfficerRegistrations(Manager manager);

	/**
     * Processes approval or rejection of officer registrations.
     *
     * @param manager the Manager processing registrations
     */
	public void processOfficerRegistration(Manager manager);

	/**
     * Processes BTO application approvals and rejections.
     *
     * @param manager the Manager handling applications
     */
	public void processBTOApplications(Manager manager);

	/**
     * Processes withdrawal requests for booked flats.
     *
     * @param manager the Manager handling withdrawals
     */
	public void processBTOWithdrawals(Manager manager);

	/**
     * Generates a summary report of applications and bookings.
     *
     * @param manager the Manager generating the report
     */
	public void generateReport(Manager manager);

	/**
     * Displays all enquiries in the system.
     *
     * @param manager the Manager viewing enquiries
     */
	public void viewAllEnquiries(Manager manager);

	/**
     * Replies to applicant enquiries.
     *
     * @param manager the Manager responding to enquiries
     */
	public void replyToEnquiries(Manager manager);
}
