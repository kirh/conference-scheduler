package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminDashboardCommand extends AbstractCommand {

    private final ConferenceService conferenceService;

    public AdminDashboardCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserPrincipal user = getUser(request);
        Long id = user.getId();
        String query;
        try {
            List<Conference> conferences = conferenceService.findConferencesByAdministratorId(id);
            request.setAttribute("conferences", conferences);
            query = "admin-dashboard";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }
}
