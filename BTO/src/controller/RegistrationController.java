package controller;

import java.util.HashMap;
import java.util.Map;

import Database.ApplicationRepository;
import Database.RegistrationRepository;
import Interface.IRegistrationServices;
import model.Application;
import model.Manager;
import model.Officer;
import model.Registration;

public class RegistrationController implements IRegistrationServices{
	private static Map<Integer, Registration> allRegistrations = RegistrationRepository.getRegistrationRepository();
	
	public Map<Integer, Registration> OfficerGetOwnRegistration(Officer officer){
    	Map<Integer, Registration> myOwnRegistrations = new HashMap<>();
    	for (Map.Entry<Integer, Registration> entry : allRegistrations.entrySet()) {
    		if (entry.getValue().getOfficer() == officer) {
    			myOwnRegistrations.put(entry.getKey(), entry.getValue());
    		}
    	}
    	return myOwnRegistrations;
    }
	
	public void addRegistration(Registration r) {
    	int id = RegistrationRepository.getidToAssign();
    	r.setID(id);
    	allRegistrations.put(id, r);
    }
	
	public Map<Integer, Registration> ManagerGetProjectRegistration(Manager manager){ // Manager gets registrations for his projects
		Map<Integer, Registration> registrations = new HashMap<>();
		for (Map.Entry<Integer, Registration> entry : allRegistrations.entrySet()) {
			if (entry.getValue().getProject().getAssignedManager() == manager) {
				registrations.put(entry.getKey(), entry.getValue());
			}
		}
		return registrations;
	}
}
