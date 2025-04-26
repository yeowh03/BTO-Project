package model;

/**
 * Represents a flat unit type within a BTO project,
 * tracking total units, available/unbooked units, and pricing.
 * <p>
 * Provides operations to assign/unassign units and adjust availability.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class UnitType {

    /** The name of this unit type (e.g., "3-Room"). */
    private String type;

    /** Total number of units originally available. */
    private int totalUnits;

    /** Price per unit. */
    private double price;

    /** Number of units currently available for booking. */
    private int availableUnits;

    /** Number of units not yet assigned to any applicant. */
    private int unbookedUnits;

    /**
     * Constructs a new UnitType with specified name, total units, and price.
     * Initializes both availableUnits and unbookedUnits to totalUnits.
     *
     * @param type       the flat type name
     * @param totalUnits the total number of units for this type
     * @param price      the price per unit
     */
    public UnitType(String type, int totalUnits, double price) {
        this.type = type;
        this.totalUnits = totalUnits;
        this.price = price;
        this.availableUnits = totalUnits;
        this.unbookedUnits = totalUnits;
    }

    /**
     * Attempts to assign (reserve) one unit of this type.
     *
     * @return {@code true} if a unit was successfully assigned; {@code false} if none left
     */
    public boolean assignUnit() {
        if (unbookedUnits > 0) {
        	// availableUnits -= 1; ---> for manager to refer to for approval of application
        	unbookedUnits -= 1;
        	return true;
        }return false;
    }

    /**
     * Attempts to unassign (release) one previously assigned unit.
     *
     * @return {@code true} if a unit was successfully unassigned; {@code false} if at max
     */
    public boolean unassignUnit() {
        if (unbookedUnits < totalUnits) {
        	unbookedUnits += 1;
        	// availableUnits += 1; ---> for manager to refer to for approval of application
            return true;
        }
        return false;
    }

    /**
     * Returns the number of units currently available for booking.
     *
     * @return availableUnits
     */
    public int getAvailableUnits() {
        return availableUnits;
    }

    /**
     * Returns the original total number of units for this type.
     *
     * @return totalUnits
     */
    public int getTotalUnits() {
        return totalUnits;
    }

    /**
     * Returns the flat type name.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the price per unit.
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Returns the number of units not yet assigned.
     *
     * @return unbookedUnits
     */
    public int getUnBookedUnits() {
        return unbookedUnits;
    }
    
    /**
     * Increments the availableUnits count by one.
     * <p>
     * Used when a booking is cancelled.</p>
     */
    public void incrementAvailableUnits() { // for manager when withdrawing
        this.availableUnits += 1;
    }

    /**
     * Decrements the availableUnits count by one.
     * <p>
     * Used when a booking is approved.</p>
     */
    public void decrementAvailableUnits() { // for manager when approving application
        this.availableUnits -= 1;
    }

    /**
     * Updates the totalUnits for this type.
     *
     * @param units the new totalUnits value
     */
    public void setTotalUnits(int units) {
        this.totalUnits = units;
    }
    
    /**
     * Updates the price per unit.
     *
     * @param px the new price
     */
    public void setPrice(double px) {
        this.price = px;
    }
}
