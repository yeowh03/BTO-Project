package model;

import java.util.HashMap;
import java.util.Map;

public class Officer extends Applicant{
	
	private Map<String, Project> handledProjects;
	
	public Officer(String name, String nric, int age, String maritalStatus, String password){
		super(name, nric, age, maritalStatus, password);
		handledProjects = new HashMap<>();
	}
	
	public void addHandledProject(String name, Project p) {
		handledProjects.put(name, p);
	}
	
	public Map<String, Project> getHandledProjects() {
		return handledProjects;
	}
}

