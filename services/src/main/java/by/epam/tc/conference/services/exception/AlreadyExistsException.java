package by.epam.tc.conference.services.exception;

import by.epam.tc.conference.commons.ConferenceException;

public class AlreadyExistsException extends ConferenceException {

    private static final long serialVersionUID = 42L;

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}


