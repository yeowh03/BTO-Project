import Database.ManagerRepository;
import Database.OfficerRepository;
import Database.ProjectRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import menu.MainMenu;
import model.Project;

/**
 * Entry point of the BTO Management System application.
 * <p>
 * Initializes sample data (a Project with unit types, stores it in the repository,
 * assigns officers to handle it), then launches the console‐based MainMenu.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Main {
	
    /**
     * Formatter for parsing and formatting project open/close dates
     * in "M/d/yyyy" format.
     */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
	
    /**
     * Application entry point.
     * <p>
     * Creates a sample Project, adds unit types, stores it into
     * the ProjectRepository, assigns it to sample officers, and
     * starts the MainMenu loop.
     * </p>
     *
     * @param args command‐line arguments (ignored)
     */
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


