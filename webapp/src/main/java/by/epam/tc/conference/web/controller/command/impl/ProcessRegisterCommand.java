package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.ErrorMessage;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessRegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ProcessRegisterCommand.class);
    private static final String REDIRECT_REGISTER = "redirect:/register";
    private static final String REDIRECT_ROOT = "redirect:/";
    private final UserService userService;
    private final Builder<? extends User> builder;

    public ProcessRegisterCommand(UserService userService, Builder<? extends User> builder) {
        this.userService = userService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.debug("Processing register form");
        User user = builder.build(request);
        try {
            UserPrincipal userPrincipal = userService.registerUser(user);
            request.changeSessionId();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_PRINCIPAL, userPrincipal);
            return REDIRECT_ROOT;
        } catch (AlreadyExistsException e) {
            logger.debug("Failed to register", e);
            request.setAttribute("error", ErrorMessage.USER_ALREADY_EXISTS);
            return REDIRECT_REGISTER;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to register");
        }
    }
}
