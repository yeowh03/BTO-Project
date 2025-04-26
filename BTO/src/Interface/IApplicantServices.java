package Interface;

import java.util.Map;
import model.Applicant;
import model.Project;

/**
 * Defines the services available to Applicants:
 * <ul>
 *   <li>browseProjects</li>
 *   <li>submitApplication</li>
 *   <li>viewApplicationStatus</li>
 *   <li>submitEnquiry</li>
 *   <li>viewEnquiries</li>
 *   <li>editEnquiry</li>
 *   <li>deleteEnquiry</li>
 *   <li>requestWithdrawal</li>
 * </ul>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IApplicantServices {

	/**
     * Retrieves all projects visible and eligible for the given applicant.
     *
     * @param applicant the applicant browsing for projects
     * @return map of project names to Project objects the applicant can view
     */
	Map<String, Project> browseProjects(Applicant applicant);

	/**
     * Submits a new application on behalf of the given applicant.
     *
     * @param applicant the applicant submitting an application
     */
	void submitApplication(Applicant applicant);

	/**
     * Displays the status of all applications for the given applicant.
     *
     * @param applicant the applicant whose application statuses to view
     */
	void viewApplicationStatus(Applicant applicant);

	/**
     * Submits a new enquiry for the given applicant.
     *
     * @param applicant the applicant submitting an enquiry
     */
	void submitEnquiry(Applicant applicant);

	/**
     * Retrieves and displays all enquiries submitted by the given applicant.
     *
     * @param applicant the applicant whose enquiries to view
     */
	void viewEnquiries(Applicant applicant);

	/**
     * Edits an existing enquiry for the given applicant.
     *
     * @param applicant the applicant editing their enquiry
     */
	void editEnquiry(Applicant applicant);

	/**
     * Deletes an existing enquiry for the given applicant.
     *
     * @param applicant the applicant whose enquiry to delete
     */
	void deleteEnquiry(Applicant applicant);

	/**
     * Requests withdrawal of a booking on behalf of the given applicant.
     *
     * @param applicant the applicant requesting withdrawal
     */
	void requestWithdrawal(Applicant applicant);
}
