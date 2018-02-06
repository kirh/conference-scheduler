package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.ErrorMessage;
import by.epam.tc.conference.web.controller.command.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminDashboardCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(AdminDashboardCommand.class);
    private static final String CONFERENCES_ATTRIBUTE = "conferences";
    private static final String ADMIN_DASHBOARD_VIEW = "admin-dashboard";

    private final ConferenceService conferenceService;

    public AdminDashboardCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.debug("loading admin dashboard");
        UserPrincipal user = getUser(request);
        if (!user.isAdmin()) {
            throw new CommandException("Page only for administrators", HttpServletResponse.SC_FORBIDDEN,
                    ErrorMessage.FORBIDDEN);
        }
        try {
            Long userId = user.getId();
            List<Conference> conferences = conferenceService.findConferencesByAdministratorId(userId);
            request.setAttribute(CONFERENCES_ATTRIBUTE, conferences);
            return ADMIN_DASHBOARD_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to load admin-dashboard");
        }
    }
}
