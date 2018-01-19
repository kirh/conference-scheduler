package by.epam.tc.conference.commons;

public class ConferenceException extends Exception {

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
