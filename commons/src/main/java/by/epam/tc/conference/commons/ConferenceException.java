package by.epam.tc.conference.commons;

/**
 * General exception for conference-scheduler application
 */
public class ConferenceException extends Exception {

    private static final long serialVersionUID = 42L;

    public ConferenceException() {
        super();
    }

    public ConferenceException(String message) {
        super(message);
    }

    public ConferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConferenceException(Throwable cause) {
        super(cause);
    }
}
