package Database;

import java.util.HashMap;
import java.util.Map;
import model.Officer;

/**
 * In‐memory repository for storing and retrieving Officer objects,
 * keyed by NRIC.
 * <p>
 * Initialized with a sample set of Officers.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class OfficerRepository {

	/**
     * Collection of all officers, keyed by NRIC.
     */
	private static HashMap<String, Officer> collOfOfficers = new HashMap<>(Map.ofEntries(
		Map.entry("T2109876H", new Officer("Daniel", "T2109876H", 36, "Single", "password")),
		Map.entry("S6543210I", new Officer("Emily", "S6543210I", 28, "Single", "password")),
		Map.entry("T1234567J", new Officer("David", "T1234567J", 29, "Married", "password"))
		));
	
		/**
     * Adds or updates an Officer in the repository.
     *
     * @param officer the Officer to add or update
     */
	public static void put(Officer officer) {
		collOfOfficers.put(officer.getNric(), officer);
	}
	
	/**
     * Retrieves the map of all Officers.
     *
     * @return the HashMap of NRIC keys to Officer objects
     */
	public static HashMap<String, Officer> getOfficerRepository() {
		return collOfOfficers;
	}
}
