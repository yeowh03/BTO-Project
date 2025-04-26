package Database;

import java.util.HashMap;
import java.util.Map;
import model.Applicant;

/**
 * In‐memory repository for storing and retrieving Applicant objects,
 * keyed by NRIC.
 * <p>
 * Initialized with a sample set of Applicants.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025 
 */
public class ApplicantRepository { // nric as key

	/**
     * Collection of all applicants, keyed by NRIC.
     */
	private static HashMap<String, Applicant> collOfApplicants = new HashMap<>(Map.ofEntries(
			Map.entry("S1234567A", new Applicant("John", "S1234567A", 35, "Single", "password")),
			Map.entry("T7654321B", new Applicant("Sarah", "T7654321B", 40, "Married", "password")),
			Map.entry("S9876543C", new Applicant("Grace", "S9876543C", 37, "Married", "password")),
			Map.entry("T2345678D", new Applicant("James", "T2345678D", 30, "Married", "password")),
			Map.entry("S3456789E", new Applicant("Rachel", "S3456789E", 25, "Single", "password"))
			));
	
	/**
     * Adds or updates an Applicant in the repository.
     *
     * @param applicant the Applicant to add or update
     */
	public static void put(Applicant applicant) {
		collOfApplicants.put(applicant.getNric(), applicant);
	}
	
	/**
     * Retrieves the map of all Applicants.
     *
     * @return the HashMap of NRIC keys to Applicant objects
     */
	public static HashMap<String, Applicant> getApplicantRepository() {
		return collOfApplicants;
	}
}
