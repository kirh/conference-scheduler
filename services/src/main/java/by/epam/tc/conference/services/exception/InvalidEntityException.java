package by.epam.tc.conference.services.exception;

import by.epam.tc.conference.commons.ConferenceException;

public class InvalidEntityException extends ConferenceException {

    private static final long serialVersionUID = 42L;

    public InvalidEntityException() {
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(Throwable cause) {
        super(cause);
    }
}
