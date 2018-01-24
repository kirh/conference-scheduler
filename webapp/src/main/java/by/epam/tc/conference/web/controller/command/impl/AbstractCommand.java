package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;

public abstract class AbstractCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AbstractCommand.class);

    protected String forbidRequest(HttpServletRequest request, HttpServletResponse response) {
        return processError(request, response, HttpURLConnection.HTTP_FORBIDDEN, "error.forbidden");
    }

    protected String processBadRequest(HttpServletRequest request, HttpServletResponse response) {
        return processError(request, response, HttpServletResponse.SC_BAD_REQUEST, "error.bad-request");
    }

    protected String processInternalError(HttpServletRequest request, HttpServletResponse response) {
        return processError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error.internal");
    }

    protected String processPageNotFound(HttpServletRequest request, HttpServletResponse response) {
        return processError(request, response, HttpServletResponse.SC_NOT_FOUND, "error.not-found");
    }

    private String processError(HttpServletRequest request, HttpServletResponse response, int code, String message){
        response.setStatus(code);
        request.setAttribute("error", message);
        return "error";
    }

    protected UserPrincipal getUser(HttpServletRequest request) {
        return (UserPrincipal) getSessionAttribute(request, SessionAttribute.USER_PRINCIPAL);
    }

    protected Long getUserId(HttpServletRequest request) {
        return getUser(request).getId();
    }

    protected Long parseIdParameter(HttpServletRequest request, String parameterName) {
        String idString = request.getParameter(parameterName);
        try {
            return Long.valueOf(idString);
        } catch (NumberFormatException e) {
            logger.debug("Error during parse '{}' value='{}'", parameterName, idString, e);
            return null;
        }
    }

    private Object getSessionAttribute(HttpServletRequest request, String attributeName){
        HttpSession session = request.getSession();
        return session.getAttribute(attributeName);
    }

    @Override
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
