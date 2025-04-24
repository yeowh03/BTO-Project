package Interface;

import java.util.Map;

import model.Applicant;
import model.Application;
import model.Officer;
import model.Project;

public interface IApplicationServices {
	public void addApplication(Application app);
	public void removeApplication(int id);
	public Map<Integer, Application> officerGetSuccessfulApplication(Officer officer);
	public Map<Integer, Application> ApplicantGetOwnApplication(Applicant applicant);
	public Map<Integer, Application> getProjectsBookedApplications(Map<String, Project> projects);
	public Map<Integer, Application> ManagerGetPendingApplications(Map<String, Project> projects);
	public Map<Integer, Application> ManagerGetCancellingApplications(Map<String, Project> projects);
}
