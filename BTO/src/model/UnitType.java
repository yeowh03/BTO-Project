package model;

import java.util.ArrayList;
import java.util.List;

public class UnitType {
    private String type;
    private int totalUnits;
    private double price;
    private int availableUnits;
    private int unbookedUnits;

    public UnitType(String type, int totalUnits, double price) {
        this.type = type;
        this.totalUnits = totalUnits;
        this.price = price;
        this.availableUnits = totalUnits;
        this.unbookedUnits = totalUnits;
    }

    public boolean assignUnit() {
        if (unbookedUnits > 0) {
        	// availableUnits -= 1; ---> for manager to refer to for approval of application
        	unbookedUnits -= 1;
        	return true;
        }return false;
    }

    public boolean unassignUnit() {
        if (unbookedUnits < totalUnits) {
        	unbookedUnits += 1;
        	// availableUnits += 1; ---> for manager to refer to for approval of application
            return true;
        }
        return false;
    }

    public int getAvailableUnits() {
        return availableUnits;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
    
    public int getUnBookedUnits() {
        return unbookedUnits;
    }
    
    public void incrementAvailableUnits() { // for manager when withdrawing
        this.availableUnits += 1;
    }
    public void decrementAvailableUnits() { // for manager when approving application
        this.availableUnits -= 1;
    }

    public void setTotalUnits(int units) {
        this.totalUnits = units;
    }
    
    public void setPrice(double px) {
        this.price = px;
    }
}
