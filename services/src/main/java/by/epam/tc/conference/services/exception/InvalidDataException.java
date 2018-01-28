package by.epam.tc.conference.services.exception;

/**
 * Thrown when invalid data given
 */
public class InvalidDataException extends ServiceException {

    private static final long serialVersionUID = 42L;

    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }
}
