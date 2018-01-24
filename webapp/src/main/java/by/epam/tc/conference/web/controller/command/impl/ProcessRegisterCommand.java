package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.exception.AlreadyExistsException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessRegisterCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessRegisterCommand.class);
    private final UserService userService;
    private final Builder<? extends User> builder;

    public ProcessRegisterCommand(UserService userService, Builder<? extends User> builder) {
        this.userService = userService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        User user = builder.build(request);
        try {
            UserPrincipal userPrincipal = userService.registerUser(user);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_PRINCIPAL, userPrincipal);
            return getDashboardPage(userPrincipal);
        } catch (AlreadyExistsException e) {
            logger.debug("Failed to register, user already exists", e);
            request.setAttribute("error", "register.error.already-exists");
            return "redirect:/register";
        } catch (ServiceException e) {
            logger.error("Failed to register", e);
            return processInternalError(request, response);
        }
    }

    private String getDashboardPage(UserPrincipal user) {
        if (user.isAdmin()) {
            return "redirect:/admin-dashboard";
        } else {
            return "redirect:/user-dashboard";
        }
    }
}
