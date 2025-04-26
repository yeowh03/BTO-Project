package model;

/**
 * Enumerates the possible statuses for a flat booking application.
 * <ul>
 *   <li>{@link #PENDING} – application submitted but not yet processed</li>
 *   <li>{@link #SUCCESSFUL} – application approved</li>
 *   <li>{@link #UNSUCCESSFUL} – application rejected</li>
 *   <li>{@link #BOOKED} – flat booked and confirmed</li>
 *   <li>{@link #PENDING_CANCEL_BOOKING} – cancellation requested before official booking</li>
 *   <li>{@link #PENDING_CANCEL} – cancellation requested after booking</li>
 * </ul>
 * <p>
 * Each enum constant carries a human‐readable display name.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public enum ApplicationStatus {

    /** Application submitted and awaiting review. */
    PENDING("Pending"),

    /** Application approved; next step is booking. */
    SUCCESSFUL("Successful"),

    /** Application rejected. */
    UNSUCCESSFUL("Unsuccessful"),

    /** Flat has been booked and confirmed. */
    BOOKED("Booked"),

    /** Cancellation requested before official booking. */
	PENDING_CANCEL_BOOKING("Cancelling"),

    /** Cancellation requested after booking. */
	PENDING_CANCEL("Cancelling");

    private final String displayName;

    /**
     * Constructs an ApplicationStatus with a display name.
     *
     * @param displayName the text shown for this status
     */
    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human‐readable name for this status.
     *
     * @return displayName
     */
    @Override
    public String toString() {
        return displayName;
    }
}
