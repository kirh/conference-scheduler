package by.epam.tc.conference.services.exception;

import by.epam.tc.conference.commons.ConferenceException;

public class EntityNotFoundException extends ConferenceException {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
