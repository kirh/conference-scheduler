package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteConferenceCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(DeleteConferenceCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private final ConferenceService conferenceService;

    public DeleteConferenceCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.traceEntry();
        String idString = request.getParameter(CONFERENCE_ID_PARAM);
        if (idString == null ) {
            return processInternalError(request, response);
        }
        Long conferenceId = Long.valueOf(idString);
        UserPrincipal user = getUser(request);
        long userId = user.getId();

        String query;
        try {
            Conference conference = conferenceService.getConference(conferenceId);
            long administratorId = conference.getAdministratorId();
            if (administratorId == userId) {
                conferenceService.deleteConferenceById(conferenceId);
                query = "forward:/admin-dashboard";
            } else {
                query = forbidRequest(request, response);
            }
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return logger.traceExit(query);
    }
}
