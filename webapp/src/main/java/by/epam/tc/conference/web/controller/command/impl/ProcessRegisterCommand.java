package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.services.UserService;
import by.epam.tc.conference.web.controller.SessionAttribute;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessRegisterCommand extends AbstractCommand {
    private final UserService userService;
    private final Builder<? extends User> builder;

    public ProcessRegisterCommand(UserService userService, Builder<? extends User> builder) {
        this.userService = userService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = builder.build(request);
        UserPrincipal userPrincipal = null;
        String query;
        try {
            userPrincipal = userService.registerUser(user);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_PRINCIPAL, userPrincipal);
            query = getDashboardPage(userPrincipal);
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }

    private String getDashboardPage(UserPrincipal user) {
        if (user.isAdmin()) {
            return "redirect:/admin-dashboard";
        } else {
            return "redirect:/user-dashboard";
        }
    }
}
