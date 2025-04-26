package controller;

import Database.ProjectRepository;
import Interface.IProjectServices;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import model.Manager;
import model.Project;
import model.UnitType;
import model.User;

/**
 * Manages all project‐related operations:
 * <ul>
 *   <li>Retrieve eligible projects for any user</li>
 *   <li>Fetch projects created by a manager or view all projects</li>
 *   <li>Lookup, add, and remove projects</li>
 *   <li>Filter projects by neighborhood and flat type</li>
 * </ul>
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025 
 */
public class ProjectController implements IProjectServices{
    /**
     * Formatter for application open/close dates (M/d/yyyy).
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    /**
     * In‐memory repository of all projects, keyed by project name.
     */
    private static Map<String, Project> allProjects = ProjectRepository.getRepository(); // projectName as key

    /**
     * Returns only those projects that are visible and for which
     * the given user meets age and marital‐status eligibility.
     *
     * @param user the User (Applicant, Officer, or Manager) to check eligibility for
     * @return map of project names to eligible Project objects
     */
    public Map<String, Project> getEligibleProjects(User user) {
        Map<String, Project> eligibleProjects = new HashMap<>();
        boolean isSingle = user.getMaritalStatus().equalsIgnoreCase("Single");
        int age = user.getAge();
        
        for (Project project : allProjects.values()) {
            if (!project.isVisible()) continue;
            
            // Check age eligibility
            if ((isSingle && age < 35) || (!isSingle && age < 21)) {
                continue;
            }

            // Singles can only apply for 2-Room
            if (isSingle) {
                Map<String, UnitType> unitTypes = project.getUnitTypes();
                if (unitTypes.get("2-Room").getTotalUnits() == 0) {
                    continue;
                }
            }
            
            eligibleProjects.put(project.getProjectName(), project);
        }
        return eligibleProjects;
    }
    
    /**
     * Retrieves all projects created by the specified manager.
     *
     * @param manager the Manager whose projects to fetch
     * @return map of project names to Project objects managed by this manager
     */
    public Map<String, Project> managerGetProjects(Manager manager){ // manager gets his own created projects
    	Map<String, Project> myProjects = new HashMap<>();
    	for(Map.Entry<String, Project> entry : allProjects.entrySet()) {
    		if (entry.getValue().getAssignedManager() == manager) {
    			myProjects.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return myProjects;
    }
    
    /**
     * Retrieves every project in the system, regardless of manager.
     *
     * @return map of all project names to their Project objects
     */
    public Map<String, Project> managerViewAllProjects(){
    	Map<String, Project> projects = new HashMap<>();
    	for(Map.Entry<String, Project> entry : allProjects.entrySet()) {  		
    		projects.put(entry.getKey(), entry.getValue());
    	}
    	return projects;
    }

    /**
     * Looks up a single project by its name.
     *
     * @param projectName the unique name of the project
     * @return the Project object if found; {@code null} otherwise
     */
    public Project getProject(String projectName) {
        return allProjects.get(projectName);
    }

    /**
     * Adds a new project to the repository.
     *
     * @param project the Project object to add
     * @return {@code true} if successfully added; {@code false} if the name already exists
     */
    public boolean addProject(Project project) {
        if (allProjects.containsKey(project.getProjectName())) {
            return false;
        }
        allProjects.put(project.getProjectName(), project);
        return true;
    }

    /**
     * Removes an existing project by name.
     *
     * @param projectName the name of the project to remove
     * @return {@code true} if successfully removed; {@code false} otherwise
     */
    public boolean removeProject(String projectName) {
        if (allProjects.remove(projectName) != null) {
            return true;
        }
        return false;
    }

    /**
     * Filters the given set of projects by neighborhood and flat type.
     *
     * @param neighborhood the neighborhood to match (empty for no filter)
     * @param flatType     the flat type to match (empty for no filter)
     * @param projects     the map of project names to Project objects to filter
     * @return a new map of project names to Project objects that match the criteria
     */
    public HashMap<String, Project> filterProjects(String neighborhood, String flatType, Map<String, Project> projects) {
    	HashMap<String, Project> filteredProjects = new HashMap<>();
        
        for (Project project : projects.values()) {
            // Filter by neighborhood if specified
            if (!neighborhood.isEmpty() && 
                !project.getNeighborhood().equalsIgnoreCase(neighborhood)) {
                continue;
            }
            
            // Filter by flat type if specified
            if (!flatType.isEmpty()) {
                Map<String, UnitType> unitTypes = project.getUnitTypes();
                if (!unitTypes.containsKey(flatType)) {
                    continue;
                }
            }
            
            filteredProjects.put(project.getProjectName(), project);
        }
        
        return filteredProjects;
    }
}
