package Database;

import java.util.HashMap;
import java.util.Map;
import model.Manager;

/**
 * In‐memory repository for storing and retrieving Manager objects,
 * keyed by NRIC.
 * <p>
 * Initialized with a sample set of Managers.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class ManagerRepository {

	/**
     * Collection of all managers, keyed by NRIC.
     */
	private static HashMap<String, Manager> collOfManagers = new HashMap<>(Map.ofEntries(
		Map.entry("T8765432F", new Manager("Michael", "T8765432F", 36, "Single", "password")),
		Map.entry("S5678901G", new Manager("Jessica", "T8765432F", 26, "Married", "password"))
		));
	
	/**
     * Adds or updates a Manager in the repository.
     *
     * @param manager the Manager to add or update
     */
	public static void put(Manager manager) {
		collOfManagers.put(manager.getNric(), manager);
	}
	
	/**
     * Retrieves the map of all Managers.
     *
     * @return the HashMap of NRIC keys to Manager objects
     */
	public static HashMap<String, Manager> getManagerRepository() {
		return collOfManagers;
	}
}