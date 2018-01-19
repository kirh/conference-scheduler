package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends AbstractCommand {

    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final int HOUR = 3600;

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String action = request.getParameter("action");
        String page;
        if ("process".equals(action)) {
            page = processLogin(request, response);
        } else {
            page = "login";
        }
        return page;
    }

    private String processLogin(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);
        try {
            UserPrincipal user = userService.authenticateUser(username, password);
            request.changeSessionId();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_PRINCIPAL, user);
            session.setMaxInactiveInterval(HOUR);
            String page;
            if (user.isAdmin()) {
                page = "redirect:/admin-dashboard";
            } else {
                page = "redirect:/user-dashboard";
            }
            return page;
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
