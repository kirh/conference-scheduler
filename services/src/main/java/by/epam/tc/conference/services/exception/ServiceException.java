package by.epam.tc.conference.services.exception;

import by.epam.tc.conference.commons.ConferenceException;

/**
 * General exception for service layer
 * Will be thrown if any error occurs
 *
 * @see AlreadyExistsException
 * @see AuthenticationException
 * @see NotFoundException
 * @see InvalidDataException
 * @see NoAuthorityException
 */
public class ServiceException extends ConferenceException {

    private static final long serialVersionUID = 42L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
