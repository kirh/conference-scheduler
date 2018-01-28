package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AuthenticationException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.web.controller.ErrorMessage;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessLoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ProcessRegisterCommand.class);
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String REDIRECT_TO_ROOT_PAGE = "redirect:/";
    private static final String LOGIN_VIEW = "login";

    private final UserService userService;

    public ProcessLoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String username = request.getParameter(PARAM_USERNAME);
        logger.debug("Attempt to login username={}", username);
        String password = request.getParameter(PARAM_PASSWORD);

        try {
            UserPrincipal user = userService.authenticateUser(username, password);
            request.changeSessionId();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_PRINCIPAL, user);
            return REDIRECT_TO_ROOT_PAGE;
        } catch (AuthenticationException e) {
            logger.debug("User doesn't exist or password mismatch username='{}'", username);
            request.setAttribute("error", "login.error");
            return LOGIN_VIEW;
        } catch (ServiceException e) {
            throw new CommandException("Failed to process login username " + username, HttpServletResponse
                    .SC_INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL);
        }
    }
}
