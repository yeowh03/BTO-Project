package Database;

import java.util.HashMap;
import java.util.Map;
import model.Application;

/**
 * In‐memory repository for storing and retrieving Application objects,
 * keyed by a unique application ID.
 * <p>
 * Also maintains an internal counter for generating new application IDs.
 * </p>
 *
 * @author 
 * @version 
 * @since 
 */
public class ApplicationRepository {
	
	/**
     * Collection of all applications in the system, keyed by application ID.
     */
	private static Map<Integer, Application> collOfApplications = new HashMap<>();

	/**
     * Counter used to generate unique IDs for new applications.
     */
	private static int idToAssign = 0;
	
	/**
     * Retrieves the in‐memory map of all applications.
     *
     * @return the map of application IDs to Application objects
     */
	public static Map<Integer, Application> getApplicationRepository() {
		return collOfApplications;
	}
	
	/**
     * Generates and returns the next unique application ID.
     * <p>
     * Each call increments the internal counter by one.
     * </p>
     *
     * @return the next available application ID
     */
	public static int getidToAssign() {
		int id = idToAssign;
		idToAssign += 1;
		return id;
	}
}