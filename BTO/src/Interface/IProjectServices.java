package Interface;

import java.util.HashMap;
import java.util.Map;
import model.Manager;
import model.Project;
import model.User;

/**
 * Defines services for managing and retrieving Project entities:
 * <ul>
 *   <li>Retrieve projects eligible for a user</li>
 *   <li>Retrieve projects created by a manager or view all projects</li>
 *   <li>Lookup, add, and remove projects</li>
 *   <li>Filter a set of projects by neighborhood and flat type</li>
 * </ul>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public interface IProjectServices{

	/**
     * Retrieves only those projects that are visible and for which
     * the given user meets eligibility criteria.
     *
     * @param user the User (Applicant, Officer, or Manager) to check eligibility for
     * @return map of project names to Project objects the user is eligible to view
     */
	public Map<String, Project> getEligibleProjects(User user);

	/**
     * Retrieves all projects created by the specified manager.
     *
     * @param manager the Manager whose projects to fetch
     * @return map of project names to Project objects managed by this manager
     */
	public Map<String, Project> managerGetProjects(Manager manager);

	/**
     * Retrieves every project in the system, regardless of manager.
     *
     * @return map of all project names to their Project objects
     */
	public Map<String, Project> managerViewAllProjects();

	/**
     * Looks up a single project by its unique name.
     *
     * @param projectName the name of the project to retrieve
     * @return the Project object if found; {@code null} otherwise
     */
	public Project getProject(String projectName);

	/**
     * Adds a new project to the system.
     *
     * @param project the Project object to add
     * @return {@code true} if successfully added; {@code false} if a project
     *         with the same name already exists
     */
	public boolean addProject(Project project);

	/**
     * Removes an existing project by its name.
     *
     * @param projectName the name of the project to remove
     * @return {@code true} if successfully removed; {@code false} otherwise
     */
	public boolean removeProject(String projectName);

	/**
     * Filters a given collection of projects by neighborhood and flat type.
     *
     * @param neighborhood the neighborhood to match (empty for no filter)
     * @param flatType     the flat type to match (empty for no filter)
     * @param projects     the map of project names to Project objects to filter
     * @return a new HashMap of project names to Project objects that match the criteria
     */
	public HashMap<String, Project> filterProjects(String neighborhood, String flatType, Map<String, Project> projects);
}
