package Interface;

import java.util.Map;

import model.Manager;
import model.Officer;
import model.Registration;

public interface IRegistrationServices {
	public Map<Integer, Registration> OfficerGetOwnRegistration(Officer officer);
	public void addRegistration(Registration r);
	public Map<Integer, Registration> ManagerGetProjectRegistration(Manager manager);
}
