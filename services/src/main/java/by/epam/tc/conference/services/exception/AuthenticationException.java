package by.epam.tc.conference.services.exception;

/**
 * Thrown when failed to authenticate with given credentials.
 */
public class AuthenticationException extends ServiceException {

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
