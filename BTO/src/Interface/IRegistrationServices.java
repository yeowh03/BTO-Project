package Interface;

import java.util.Map;
import model.Manager;
import model.Officer;
import model.Registration;

/**
 * Defines registration services for both Officers and Managers:
 * <ul>
 *   <li>Retrieve an officer’s own registrations</li>
 *   <li>Add a new registration</li>
 *   <li>Retrieve registrations for a manager’s projects</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IRegistrationServices {

	/**
     * Retrieves all registrations submitted by a specific officer.
     *
     * @param officer the Officer whose registrations to fetch
     * @return map of registration IDs to Registration objects for that officer
     */
	public Map<Integer, Registration> OfficerGetOwnRegistration(Officer officer);

	/**
     * Adds a new registration to the system.
     *
     * @param r the Registration object to add
     */
	public void addRegistration(Registration r);

	/**
     * Retrieves all registrations for projects managed by the given manager.
     *
     * @param manager the Manager whose project registrations to fetch
     * @return map of registration IDs to Registration objects for those projects
     */
	public Map<Integer, Registration> ManagerGetProjectRegistration(Manager manager);
}
