package by.epam.tc.conference.services.exception;

import by.epam.tc.conference.commons.ConferenceException;

public class AuthenticationException extends ConferenceException {

    private static final long serialVersionUID = 42L;

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
