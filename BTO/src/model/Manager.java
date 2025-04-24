package model;

import java.util.HashMap;
import java.util.Map;

public class Manager extends User{
//	private Map<String, Project> myProjects;
	
	public Manager(String name, String nric, int age, String maritalStatus, String password){
		super(name, nric, age, maritalStatus, password);
//		myProjects = new HashMap<String, Project>();
	}
	
//	public void addProject(Project proj) {
//		myProjects.put(proj.getProjectName(), proj);
//	}
//	
//	public void removeProject(Project proj) {
//		myProjects.remove(proj.getProjectName());
//	}
}
