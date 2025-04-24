package model;

public class Applicant extends User{
	
	private String flatTypeBooked = "";
	
	public Applicant(String name, String nric, int age, String maritalStatus, String password) {
		super(name, nric, age, maritalStatus, password);
	}
	
	public String getflatTypeBooked() {
	if (flatTypeBooked == null || flatTypeBooked.isEmpty()) {
		System.out.println("No flat has been booked.");
	} else {
    System.out.println(flatTypeBooked);
}
		return flatTypeBooked;
	}
	
	public void setflatTypeBooked(String newFlatType) {
		this.flatTypeBooked = newFlatType;
	}
	
}
