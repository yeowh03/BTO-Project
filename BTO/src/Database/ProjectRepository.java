package Database;

import java.util.HashMap;
import java.util.Map;
import model.Project;

/**
 * In‐memory repository for storing and retrieving Project objects,
 * keyed by project name.
 * <p>
 * Initialized empty; projects are added dynamically at runtime.
 * </p>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class ProjectRepository {

	/**
     * Collection of all projects, keyed by project name.
     */
	private static Map<String, Project> collOfProjects = new HashMap<>() ;
	
	/**
     * Retrieves the in‐memory map of all projects.
     *
     * @return the map of project names to Project objects
     */
	public static Map<String, Project> getRepository() {
		return collOfProjects;
	}
}


