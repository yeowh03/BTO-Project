package controller;

import Database.RegistrationRepository;
import Interface.IRegistrationServices;
import java.util.HashMap;
import java.util.Map;
import model.Manager;
import model.Officer;
import model.Registration;

/**
 * Implements registration services for both officers and managers:
 * <ul>
 *   <li>Add new registrations</li>
 *   <li>Retrieve an officer’s own registrations</li>
 *   <li>Retrieve all registrations for projects managed by a manager</li>
 * </ul>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025 
 */
public class RegistrationController implements IRegistrationServices{

	/**
     * In‐memory repository of registrations, keyed by registration ID.
     */
	private static Map<Integer, Registration> allRegistrations = RegistrationRepository.getRegistrationRepository();
	
	/**
     * Retrieves all registrations submitted by a specific officer.
     *
     * @param officer the Officer whose registrations to fetch
     * @return map of registration IDs to Registration objects for that officer
     */
	public Map<Integer, Registration> OfficerGetOwnRegistration(Officer officer){
    	Map<Integer, Registration> myOwnRegistrations = new HashMap<>();
    	for (Map.Entry<Integer, Registration> entry : allRegistrations.entrySet()) {
    		if (entry.getValue().getOfficer() == officer) {
    			myOwnRegistrations.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return myOwnRegistrations;
    }
	
	/**
     * Adds a new registration to the system.
     * Assigns a unique ID to the registration.
     *
     * @param r the Registration object to add
     */
	public void addRegistration(Registration r) {
    	int id = RegistrationRepository.getidToAssign();
    	r.setID(id);
    	allRegistrations.put(id, r);
    }
	
	/**
     * Retrieves all registrations for projects owned by the given manager.
     *
     * @param manager the Manager whose project registrations to fetch
     * @return map of registration IDs to Registration objects for those projects
     */
	public Map<Integer, Registration> ManagerGetProjectRegistration(Manager manager){ // Manager gets registrations for his projects
		Map<Integer, Registration> registrations = new HashMap<>();
		for (Map.Entry<Integer, Registration> entry : allRegistrations.entrySet()) {
			if (entry.getValue().getProject().getAssignedManager() == manager) {
				registrations.put(entry.getKey(), entry.getValue());
			}
		}
		return registrations;
	}
}
