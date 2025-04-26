package Database;

import java.util.HashMap;
import java.util.Map;
import model.Enquiry;

/**
 * In‐memory repository for storing and retrieving Enquiry objects,
 * keyed by a unique enquiry ID.
 * <p>
 * Also maintains an internal counter for generating new enquiry IDs.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class EnquiryRepository {

	/**
     * Collection of all enquiries in the system, keyed by enquiry ID.
     */
	private static Map<Integer, Enquiry> collOfEnquiries = new HashMap<>();

	/**
     * Counter used to generate unique IDs for new enquiries.
     */
	private static int idToAssign = 0;
	
	/**
     * Retrieves the in‐memory map of all enquiries.
     *
     * @return the map of enquiry IDs to Enquiry objects
     */
	public static Map<Integer, Enquiry> getRepository() {
		return collOfEnquiries;
	}
	
	/**
     * Generates and returns the next unique enquiry ID.
     * <p>
     * Each call increments the internal counter by one.
     * </p>
     *
     * @return the next available enquiry ID
     */
	public static int getidToAssign() {
		int id = idToAssign;
		idToAssign += 1;
		return id;
	}
}