package auth;

import Database.ApplicantRepository;
import Database.ManagerRepository;
import Database.OfficerRepository;
import java.util.HashMap;
import model.Applicant;
import model.Manager;
import model.Officer;
import model.User;
import model.UserType;

/**
 * Handles authentication of users (Applicants, Officers, Managers)
 * by verifying NRIC format and checking credentials against
 * the appropriate repository.
 *
 * @author FCSH Group 2
 * @version final
 * @since 24/4/2025
 */
public class AuthenticationSystem {
    /**
     * In-memory repository of Applicants, keyed by NRIC.
     */
    private static HashMap<String, Applicant> applicantRepository =
            ApplicantRepository.getApplicantRepository();

    /**
     * In-memory repository of Officers, keyed by NRIC.
     */
    private static HashMap<String, Officer> officerRepository =
            OfficerRepository.getOfficerRepository();

    /**
     * In-memory repository of Managers, keyed by NRIC.
     */
    private static HashMap<String, Manager> managerRepository =
            ManagerRepository.getManagerRepository();

    /**
     * Attempt to log in a user.
     * <p>
     * First validates the NRIC format. Then checks the given password
     * against the stored password in the appropriate repository based
     * on the UserType.
     *
     * @param nric     the user’s NRIC (must be 9 chars: S/T + 7 digits + letter)
     * @param password the plaintext password to verify
     * @param type     the kind of user to authenticate (APPLICANT, OFFICER, MANAGER)
     * @return the matched User if credentials are correct; {@code null} otherwise
     */
    public User login(String nric, String password, UserType type) {
        if (!isValidNRIC(nric)) {
            return null;
        }

        switch (type) {
            case APPLICANT:
                if (applicantRepository.containsKey(nric)
                    && applicantRepository.get(nric).getPassword().equals(password)) {
                    return applicantRepository.get(nric);
                }
                break;

            case OFFICER:
                if (officerRepository.containsKey(nric)
                    && officerRepository.get(nric).getPassword().equals(password)) {
                    return officerRepository.get(nric);
                }
                break;

            case MANAGER:
                if (managerRepository.containsKey(nric)
                    && managerRepository.get(nric).getPassword().equals(password)) {
                    return managerRepository.get(nric);
                }
                break;

            default:
                return null;
        }

        return null;
    }

    /**
     * Checks whether an NRIC string is valid:
     * <ul>
     *   <li>Not null and exactly 9 characters</li>
     *   <li>Starts with 'S' or 'T'</li>
     *   <li>Next 7 characters are digits</li>
     *   <li>Last character is a letter</li>
     * </ul>
     *
     * @param nric the NRIC string to validate
     * @return {@code true} if the format is valid; {@code false} otherwise
     */
    private boolean isValidNRIC(String nric) {
        if (nric == null || nric.length() != 9) {
            return false;
        }

        // First character must be 'S' or 'T'
        char firstChar = nric.charAt(0);
        if (firstChar != 'S' && firstChar != 'T') {
            return false;
        }

        // Middle 7 characters must be digits
        String digits = nric.substring(1, 8);
        if (!digits.matches("\\d{7}")) {
            return false;
        }

        // Last character must be a letter
        return Character.isLetter(nric.charAt(8));
    }
}