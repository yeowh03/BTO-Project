import Database.ManagerRepository;
import Database.OfficerRepository;
import Database.ProjectRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import menu.MainMenu;
import model.Project;

public class Main {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
	
    public static void main(String[] args) {
        System.out.println("Starting BTO Management System...");
        
        // to initialise the database with the sample project
        Project p = new Project("NTU", "Yishun", LocalDate.parse("2/15/2025", DATE_FORMATTER), LocalDate.parse("10/20/2025", DATE_FORMATTER), ManagerRepository.getManagerRepository().get("S5678901G"), 3);
        p.addUnitType("2-Room", 2, 350000);
        p.addUnitType("3-Room", 3, 450000);
        
        
        ProjectRepository.getRepository().put(p.getProjectName(), p);
        
        // update the officers' profiles
        OfficerRepository.getOfficerRepository().get("T2109876H").addHandledProject("NTU", p);
        OfficerRepository.getOfficerRepository().get("S6543210I").addHandledProject("NTU", p);
        
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMainMenu();
    }
}


