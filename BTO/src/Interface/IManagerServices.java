package Interface;

import model.Manager;

public interface IManagerServices {
	public void createProject(Manager manager);
	public void editProject(Manager manager);
	public void deleteProject(Manager manager);
	public void viewAllProjects(Manager manager);
	public void viewMyProjects(Manager manager);
	public void togglesVisibility(Manager manager);
	public void viewOfficerRegistrations(Manager manager);
	public void processOfficerRegistration(Manager manager);
	public void processBTOApplications(Manager manager);
	public void processBTOWithdrawals(Manager manager);
	public void generateReport(Manager manager);
	public void viewAllEnquiries(Manager manager);
	public void replyToEnquiries(Manager manager);
}
