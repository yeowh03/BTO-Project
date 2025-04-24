package Interface;

import java.util.HashMap;

import java.util.Map;

import model.Manager;
import model.Project;
import model.User;

public interface IProjectServices{
//	public Map<String, Project> getAssignedProjects(Officer officer);
	public Map<String, Project> getEligibleProjects(User user);
	public Map<String, Project> managerGetProjects(Manager manager);
	public Map<String, Project> managerViewAllProjects();
	public Project getProject(String projectName);
	public boolean addProject(Project project);
	public boolean removeProject(String projectName);
	public HashMap<String, Project> filterProjects(String neighborhood, String flatType, Map<String, Project> projects);
}
