package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.web.SessionAttribute;
import by.epam.tc.conference.web.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RootPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RootPageCommand.class);
    private static final String FORWARD_ADMIN_DASHBOARD = "forward:/admin-dashboard";
    private static final String FORWARD_USER_DASHBOARD = "forward:/user-dashboard";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Loading root page");
        HttpSession session = request.getSession();
        UserPrincipal user = (UserPrincipal) session.getAttribute(SessionAttribute.USER_PRINCIPAL);
        if (user.isAdmin()) {
            return FORWARD_ADMIN_DASHBOARD;
        }
        return FORWARD_USER_DASHBOARD;
    }
}
