package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AuthenticationException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.web.controller.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessLoginCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessRegisterCommand.class);
    private static final int HOUR = 3600;
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private final UserService userService;

    public ProcessLoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(PARAM_USERNAME);
        logger.traceEntry("username = '{}'", username);
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
            return logger.traceExit(page);
        } catch (AuthenticationException e) {
            logger.debug("User doesn't exist or password mismatch username='{}'", username);
            request.setAttribute("error", "login.error");
            return "login";
        } catch (ServiceException e) {
            logger.error("Failed to process login username='{}'", username, e);
            return processInternalError(request, response);
        }
    }
}
