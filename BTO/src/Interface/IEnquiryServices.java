package Interface;

import java.util.Map;

import model.Applicant;
import model.Enquiry;
import model.Project;

public interface IEnquiryServices{
	public Map<Integer, Enquiry> getProjectsEnquiries(Map<String, Project> projects);
	public void deleteEnquiry(Enquiry enquiry);
	public Map<Integer, Enquiry> applicantGetEnquiries(Applicant applicant);
	public void submitEnquiry(Enquiry enquiry);
	public Map<Integer, Enquiry> ManagerGetAllEnquiries();
	
}
