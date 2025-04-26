package Interface;

import model.Officer;

/**
 * Defines services available to Officers:
 * <ul>
 *   <li>Submit applications</li>
 *   <li>Process flat selection</li>
 *   <li>Register for projects</li>
 *   <li>View registration status</li>
 *   <li>View project details</li>
 *   <li>View and reply to enquiries</li>
 *   <li>Generate booking receipts</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IOfficerServices extends IApplicantServices{

	/**
     * Submits an application on behalf of the given officer.
     *
     * @param officer the Officer performing the submission
     */
	void submitApplication(Officer officer);

	/**
     * Processes the flat selection step after a booking.
     *
     * @param officer the Officer handling flat selection
     */
	void processFlatSelection(Officer officer);

	/**
     * Registers the officer to handle a new BTO project.
     *
     * @param officer the Officer registering for the project
     */
	void registerForProject(Officer officer);

	/**
     * Views the status of all project registrations by this officer.
     *
     * @param officer the Officer whose registration status to view
     */
	void viewRegistrationStatus(Officer officer);

	/**
     * Displays detailed information for projects this officer manages.
     *
     * @param officer the Officer viewing project details
     */
	void viewProjectDetails(Officer officer);

	/**
     * Retrieves and displays all enquiries related to projects this officer handles.
     *
     * @param officer the Officer viewing project enquiries
     */
	void viewProjectEnquiries(Officer officer);

	/**
     * Allows the officer to reply to applicant enquiries.
     *
     * @param officer the Officer responding to enquiries
     */
	void replyToEnquiries(Officer officer);

	/**
     * Generates and prints a receipt for a completed booking.
     *
     * @param officer the Officer generating the receipt
     */
	void generateReceipt(Officer officer);
}
