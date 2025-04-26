package model;

/**
 * Enumerates the possible statuses for officer registration requests.
 * <ul>
 *   <li>{@link #PENDING} – registration submitted but not yet processed</li>
 *   <li>{@link #SUCCESSFUL} – registration approved</li>
 *   <li>{@link #UNSUCCESSFUL} – registration rejected</li>
 * </ul>
 * <p>
 * Each enum constant carries a human‐readable display name.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public enum RegistrationStatus {

    /** Registration submitted and awaiting review. */
    PENDING("Pending"),

    /** Registration approved. */
    SUCCESSFUL("Approved"),

    /** Registration rejected. */
    UNSUCCESSFUL("Rejected");

    /** Human‐readable name for this status. */
    private final String displayName;

    /**
     * Constructs a RegistrationStatus with the specified display name.
     *
     * @param displayName the text shown for this status
     */
	RegistrationStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human‐readable name of this status.
     *
     * @return the display name for this status
     */
    @Override
    public String toString() {
        return displayName;
    }
}



