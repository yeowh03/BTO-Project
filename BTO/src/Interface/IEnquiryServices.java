package Interface;

import java.util.Map;
import model.Applicant;
import model.Enquiry;
import model.Project;

/**
 * Defines enquiry‐management services for applicants, officers, and managers:
 * <ul>
 *   <li>Submit a new enquiry</li>
 *   <li>Retrieve an applicant’s own enquiries</li>
 *   <li>Delete an existing enquiry</li>
 *   <li>Retrieve enquiries for specific projects</li>
 *   <li>Retrieve all enquiries for managers</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IEnquiryServices{
	/**
     * Retrieves all enquiries associated with the given set of projects.
     *
     * @param projects map of project names to Project objects to search
     * @return map of enquiry IDs to Enquiry objects matching those projects
     */
	public Map<Integer, Enquiry> getProjectsEnquiries(Map<String, Project> projects);

	/**
     * Deletes an existing enquiry from the system.
     *
     * @param enquiry the Enquiry object to delete
     */
	public void deleteEnquiry(Enquiry enquiry);

	/**
     * Retrieves all enquiries submitted by a specific applicant.
     *
     * @param applicant the Applicant whose enquiries to retrieve
     * @return map of enquiry IDs to Enquiry objects for that applicant
     */
	public Map<Integer, Enquiry> applicantGetEnquiries(Applicant applicant);

	/**
     * Submits a new enquiry into the system.
     *
     * @param enquiry the Enquiry object to submit
     */
	public void submitEnquiry(Enquiry enquiry);

	/**
     * Retrieves all enquiries in the system (for manager overview).
     *
     * @return map of all enquiry IDs to Enquiry objects
     */
	public Map<Integer, Enquiry> ManagerGetAllEnquiries();
	
}
