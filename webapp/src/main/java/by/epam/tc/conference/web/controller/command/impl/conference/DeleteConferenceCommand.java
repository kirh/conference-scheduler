package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteConferenceCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(DeleteConferenceCommand.class);

    private static final String CONFERENCE_ID_PARAM = "id";
    private static final String REDIRECT_ADMIN_DASHBOARD = "redirect:/admin-dashboard";
    private final ConferenceService conferenceService;

    public DeleteConferenceCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();

        Long id = parseIdParameter(request, CONFERENCE_ID_PARAM);
        if (id == null) {
            logger.debug("Failed to delete conference. Incorrect conference id");
            return processBadRequest(request, response);
        }
        try {
            Conference conference = conferenceService.getConference(id);
            long userId = getUserId(request);
            long administratorId = conference.getAdministratorId();
            String query;
            if (administratorId == userId) {
                conferenceService.deleteConferenceById(id);
                query = REDIRECT_ADMIN_DASHBOARD;
            } else {
                logger.debug("Failed to delete conference id={}. User id={} is not the owner", id, userId);
                query = forbidRequest(request, response);
            }
            return query;
        } catch (ServiceException e) {
            logger.error("Internal Error. Failed to delete conference id={}", e);
            return processInternalError(request, response);
        } catch (EntityNotFoundException e) {
            // no conference then nothing to delete
            return REDIRECT_ADMIN_DASHBOARD;
        }
    }
}
