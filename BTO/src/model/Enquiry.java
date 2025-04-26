package model;

/**
 * Represents a project enquiry submitted by an applicant.
 * <p>
 * Tracks the enquiry question, the associated project, the submitting applicant,
 * and an optional reply from an officer or manager.
 * </p>
 * 
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class Enquiry {

    /** Unique identifier for this enquiry. */
    private int id; 

    /** The text of the question posed by the applicant. */
    private String question = "";

    /** The reply text set by an officer or manager. */
    private String reply = "";

    /** The Applicant (or officer acting as applicant) who submitted this enquiry. */
    private Applicant applicant; // can be officer or applicant

    /** The Project to which this enquiry pertains. */
    private Project project;

    /**
     * Constructs a new Enquiry with the specified question, project, and applicant.
     * <p>
     * The enquiry ID must be assigned separately via {@link #setID(int)},
     * and the reply can be set later via {@link #setReply(String)}.
     * </p>
     *
     * @param question  the enquiry question text
     * @param project   the Project this enquiry is about
     * @param applicant the Applicant who submitted the enquiry
     */
    public Enquiry(String question, Project project, Applicant applicant) {
        this.question = question;
        this.project = project;
        this.applicant = applicant;
    }
    
    /**
     * Returns the unique ID of this enquiry.
     *
     * @return enquiry ID
     */
    public int getID() {
    	return this.id;
    }
    
    /**
     * Returns the question text of this enquiry.
     *
     * @return question text
     */
    public String getQuestion() {
    	return this.question;
    }
    
    /**
     * Returns the reply text for this enquiry.
     *
     * @return reply text, or an empty string if none
     */
    public String getReply() {
    	return this.reply;
    }
    
    /**
     * Returns the applicant who submitted this enquiry.
     *
     * @return the submitting Applicant
     */
    public Applicant getApplicant() {
    	return this.applicant;
    }
    
    /**
     * Returns the project associated with this enquiry.
     *
     * @return the related Project
     */
    public Project getProject() {
    	return this.project;
    }
    
    /**
     * Sets the reply text for this enquiry.
     *
     * @param r the reply message to record
     */
    public void setReply(String r) {
    	this.reply = r;
    }
    
    /**
     * Updates the question text of this enquiry.
     *
     * @param q the new question text
     */
    public void setQuestion(String q) {
    	this.question = q;
    }
    
    /**
     * Assigns the unique identifier for this enquiry.
     *
     * @param id the enquiry ID to set
     */
    public void setID(int id) {
    	this.id = id;
    }
    
    /**
     * Returns a multi‐line string representation of this enquiry,
     * including ID, project name, question, and reply.
     *
     * @return formatted enquiry details
     */
    @Override
    public String toString() {
        return "ID: " + applicant.getName() + "\nProject: " + project.getProjectName() + "\nQuestion: " + question + "\nReply: " + reply + "\n";
    }
}
