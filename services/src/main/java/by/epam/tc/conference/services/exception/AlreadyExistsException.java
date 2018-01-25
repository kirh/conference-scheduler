package by.epam.tc.conference.services.exception;

public class AlreadyExistsException extends ServiceException {

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


