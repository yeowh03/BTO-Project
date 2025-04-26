package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Build‐To‐Order (BTO) project, including its name,
 * neighborhood, unit types, application period, assigned manager,
 * officer slots, and visibility status.
 * <p>
 * Provides operations to add new flat types, check unit availability,
 * and determine if applications are currently open.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Project {

    /** Unique name of the project. */
    private String projectName;

    /** Neighborhood where the project is located. */
    private String neighborhood;

    /** Map of flat type names to their UnitType details. */
    private Map<String, UnitType> unitTypes;

    /** Date when applications open. */
    private LocalDate openingDate;

    /** Date when applications close. */
    private LocalDate closingDate;

    /** Manager assigned to oversee this project. */
    private Manager assignedManager;

    /** Number of officer slots available for this project. */
    private int officerSlots;

    /** Whether the project is currently visible to applicants. */
    private boolean isVisible;  // Project visibility

    /**
     * Constructs a new Project with the specified attributes.
     * Initializes the unitTypes map and sets visibility to true.
     *
     * @param projectName     the name of the project
     * @param neighborhood    the neighborhood location
     * @param openingDate     date when applications open
     * @param closingDate     date when applications close
     * @param assignedManager the Manager responsible for this project
     * @param officerSlots    initial number of officer slots
     */
    public Project(String projectName, String neighborhood, LocalDate openingDate, 
                   LocalDate closingDate, Manager assignedManager, int officerSlots) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.assignedManager = assignedManager;
        this.officerSlots = officerSlots;
        this.unitTypes = new HashMap<>();
//        this.assignedOfficers = new HashMap<>();
        this.isVisible = true;  // Projects are hidden by default
    }

    /**
     * Adds a new unit type to this project.
     *
     * @param type       the flat type name (e.g., "3-Room")
     * @param totalUnits the total number of units available initially
     * @param price      the price per unit
     */
    public void addUnitType(String type, int totalUnits, double price) {
        unitTypes.put(type, new UnitType(type, totalUnits, price));
    }

    /**
     * Checks if there are any available units of the specified type.
     *
     * @param type the flat type to check availability for
     * @return {@code true} if at least one unit is available; {@code false} otherwise
     */
    public boolean hasAvailableUnits(String type) {
        UnitType unitType = unitTypes.get(type);
        return unitType != null && unitType.getAvailableUnits() > 0;
    }

    /**
     * Determines whether applications are currently open based on today's date.
     *
     * @return {@code true} if today is between openingDate and closingDate (inclusive)
     */
    public boolean isApplicationOpen() {
        LocalDate now = LocalDate.now();
        return !now.isBefore(openingDate) && !now.isAfter(closingDate);
    }

    // Getters

    /**
     * Returns the project’s unique name.
     *
     * @return projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the neighborhood of the project.
     *
     * @return neighborhood
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Returns the map of flat types available for this project.
     *
     * @return unitTypes
     */
    public Map<String, UnitType> getUnitTypes() {
        return unitTypes;
    }

    /**
     * Returns the application opening date.
     *
     * @return openingDate
     */
    public LocalDate getOpeningDate() {
        return openingDate;
    }

    /**
     * Returns the application closing date.
     *
     * @return closingDate
     */
    public LocalDate getClosingDate() {
        return closingDate;
    }

    /**
     * Returns the Manager assigned to this project.
     *
     * @return assignedManager
     */
    public Manager getAssignedManager() {
        return assignedManager;
    }

    /**
     * Returns the number of officer slots available.
     *
     * @return officerSlots
     */
    public int getOfficerSlots() {
        return officerSlots;
    }

    /**
     * Returns whether the project is visible to applicants.
     *
     * @return {@code true} if visible; {@code false} otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }

    // Setters

    /**
     * Sets the application opening date.
     *
     * @param openingDate the new opening date
     */
    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Sets the application closing date.
     *
     * @param closingDate the new closing date
     */
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Assigns a new Manager to oversee this project.
     *
     * @param assignedManager the Manager to assign
     */
    public void setAssignedManager(Manager assignedManager) {
        this.assignedManager = assignedManager;
    }

    /**
     * Sets the number of officer slots available.
     *
     * @param officerSlots the number of slots to assign
     */
    public void setOfficerSlots(int officerSlots) {
        this.officerSlots = officerSlots;
    }

    /**
     * Sets the visibility of this project for applicants.
     *
     * @param visible {@code true} to make visible; {@code false} to hide
     */
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }
}
