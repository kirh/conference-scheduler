package by.epam.tc.conference.web.controller.command;

import by.epam.tc.conference.commons.ConferenceException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.ErrorMessage;

import javax.servlet.http.HttpServletResponse;

/**
 * Thrown when exception during command processing occurs. Contains http error code and message for user.
 */
public class CommandException extends ConferenceException {

    private static final long serialVersionUID = 42L;

    private final int errorCode;

    private final String errorMessage;

    /**
     * Constructs new exception
     *
     * @param message      the detail message
     * @param errorCode    http error code
     * @param errorMessage message for user
     */
    public CommandException(String message, int errorCode, String errorMessage) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Constructs new exception with specified cause.
     *
     * @param message      the detail message
     * @param errorCode    http error code
     * @param errorMessage message for user
     * @param cause        the cause
     */
    public CommandException(String message, int errorCode, String errorMessage, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Factory method for constructing CommandException from {@link ServiceException} with
     * standard error code and user error message values.
     * @param serviceException exception to wrap
     * @param message the details message
     * @return CommandException with standard values
     */
    public static CommandException from(ServiceException serviceException, String message) {
        final String fullMessage = serviceException.getMessage() + " " + message;
        if (serviceException instanceof InvalidDataException) {
            return new CommandException(fullMessage, HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.BAD_REQUEST, serviceException);
        }
        if (serviceException instanceof NoAuthorityException) {
            return new CommandException(fullMessage, HttpServletResponse.SC_FORBIDDEN, ErrorMessage.FORBIDDEN, serviceException);
        }

        if (serviceException instanceof NotFoundException) {
            return new CommandException(fullMessage, HttpServletResponse.SC_NOT_FOUND, ErrorMessage.NOT_FOUND, serviceException);
        }
        return new CommandException(fullMessage, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorMessage.NOT_FOUND,
                serviceException);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
