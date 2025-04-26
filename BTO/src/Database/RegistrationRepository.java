package Database;

import java.util.HashMap;
import java.util.Map;
import model.Registration;

/**
 * In‐memory repository for storing and retrieving Registration objects,
 * keyed by a unique registration ID.
 * <p>
 * Also maintains an internal counter for generating new registration IDs.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class RegistrationRepository {

	/**
     * Collection of all registrations, keyed by registration ID.
     */
	private static Map<Integer, Registration> collOfRegistrations = new HashMap<>();

	/**
     * Counter used to generate unique registration IDs.
     */
	private static int idToAssign = 0;
	
	/**
     * Retrieves the in‐memory map of all registrations.
     *
     * @return the map of registration IDs to Registration objects
     */
	public static Map<Integer, Registration> getRegistrationRepository() {
		return collOfRegistrations;
	}
	
	/**
     * Generates and returns the next unique registration ID.
     * <p>
     * Each call increments the internal counter by one.
     * </p>
     *
     * @return the next available registration ID
     */
	public static int getidToAssign() {
		int id = idToAssign;
		idToAssign += 1;
		return id;
	}
}
