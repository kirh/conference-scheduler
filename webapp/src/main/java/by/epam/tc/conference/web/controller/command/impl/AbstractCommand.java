package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.ErrorMessage;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AbstractCommand implements Command {

    protected UserPrincipal getUser(HttpServletRequest request) {
        return (UserPrincipal) getSessionAttribute(request, SessionAttribute.USER_PRINCIPAL);
    }

    protected Long getUserId(HttpServletRequest request) {
        return getUser(request).getId();
    }

    protected Long parseIdParameter(HttpServletRequest request, String parameterName)
            throws CommandException {
        String idString = request.getParameter(parameterName);
        try {
            return Long.valueOf(idString);
        } catch (NumberFormatException e) {
            throw new CommandException("Error during parse '" + parameterName + "'", HttpServletResponse
                    .SC_BAD_REQUEST, ErrorMessage.BAD_REQUEST);
        }
    }

    private Object getSessionAttribute(HttpServletRequest request, String attributeName) {
        HttpSession session = request.getSession();
        return session.getAttribute(attributeName);
    }

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
