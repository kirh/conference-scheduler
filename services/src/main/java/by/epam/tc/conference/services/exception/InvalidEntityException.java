package by.epam.tc.conference.services.exception;

public class InvalidEntityException extends ServiceException {

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
