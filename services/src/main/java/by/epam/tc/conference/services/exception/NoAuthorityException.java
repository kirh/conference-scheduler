package by.epam.tc.conference.services.exception;

/**
 * Thrown when user has no permission to perform operation
 */
public class NoAuthorityException extends ServiceException {

    private static final Long serialVersionUID = 42L;

    public NoAuthorityException(String message) {
        super(message);
    }

    public NoAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAuthorityException(Throwable cause) {
        super(cause);
    }
}
