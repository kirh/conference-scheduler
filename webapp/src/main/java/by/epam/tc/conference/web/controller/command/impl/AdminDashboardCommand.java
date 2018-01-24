package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminDashboardCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(AdminDashboardCommand.class);
    private static final String CONFERENCES_ATTRIBUTE = "conferences";
    private final ConferenceService conferenceService;

    public AdminDashboardCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        UserPrincipal user = getUser(request);
        Long id = user.getId();
        try {
            List<Conference> conferences = conferenceService.findConferencesByAdministratorId(id);
            request.setAttribute(CONFERENCES_ATTRIBUTE, conferences);
            return logger.traceExit("admin-dashboard");
        } catch (ServiceException e) {
            logger.error("Failed to load admin-dashboard. Internal error", e);
            return processInternalError(request, response);
        }
    }
}
