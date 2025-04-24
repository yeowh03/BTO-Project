package controller;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Project;
import model.UnitType;
import model.User;
import model.Enquiry;
import model.Manager;
import model.Officer;
import Database.ProjectRepository;
import Interface.IProjectServices;

public class ProjectController implements IProjectServices{
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static Map<String, Project> allProjects = ProjectRepository.getRepository(); // projectName as key

//    public Map<String, Project> getAssignedProjects(Officer officer){
//    	Map<String, Project> assignedProjects = new HashMap<>();
//    	for(Map.Entry<String, Project> entry : allProjects.entrySet()) {
//    		if (entry.getValue().getAssignedOfficers().containsKey(officer.getNric())) {
//    			assignedProjects.put(entry.getKey(), entry.getValue());
//    		}
//    	}
//    	return assignedProjects;
//    }

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
    
    public Map<String, Project> managerGetProjects(Manager manager){ // manager gets his own created projects
    	Map<String, Project> myProjects = new HashMap<>();
    	for(Map.Entry<String, Project> entry : allProjects.entrySet()) {
    		if (entry.getValue().getAssignedManager() == manager) {
    			myProjects.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return myProjects;
    }
    
    public Map<String, Project> managerViewAllProjects(){
    	Map<String, Project> projects = new HashMap<>();
    	for(Map.Entry<String, Project> entry : allProjects.entrySet()) {  		
    		projects.put(entry.getKey(), entry.getValue());
    	}
    	return projects;
    }

    public Project getProject(String projectName) {
        return allProjects.get(projectName);
    }

    public boolean addProject(Project project) {
        if (allProjects.containsKey(project.getProjectName())) {
            return false;
        }
        allProjects.put(project.getProjectName(), project);
        return true;
    }

    public boolean removeProject(String projectName) {
        if (allProjects.remove(projectName) != null) {
            return true;
        }
        return false;
    }

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
