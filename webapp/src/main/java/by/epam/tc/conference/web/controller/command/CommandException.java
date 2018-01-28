package by.epam.tc.conference.web.controller.command;

import by.epam.tc.conference.commons.ConferenceException;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.ErrorMessage;

import javax.servlet.http.HttpServletResponse;

public class CommandException extends ConferenceException {

    private static final long serialVersionUID = 42L;

    private final int errorCode;

    private final String errorMessage;

    public CommandException(String message, int errorCode, String errorMessage) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommandException(String message, int errorCode, String errorMessage, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommandException(int errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static CommandException from(ServiceException serviceException, String message) {
        if (serviceException instanceof InvalidDataException) {
            return new CommandException(message, HttpServletResponse.SC_BAD_REQUEST, ErrorMessage.BAD_REQUEST, serviceException);
        }
        if (serviceException instanceof NoAuthorityException) {
            return new CommandException(message, HttpServletResponse.SC_FORBIDDEN, ErrorMessage.FORBIDDEN, serviceException);
        }

        if (serviceException instanceof NotFoundException) {
            return new CommandException(message, HttpServletResponse.SC_NOT_FOUND, ErrorMessage.NOT_FOUND, serviceException);
        }
        return new CommandException(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorMessage.NOT_FOUND, serviceException);
    }
}
