package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import by.epam.tc.conference.web.controller.command.impl.proposal.UpdateProposalStatusCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateConferenceCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(UpdateProposalStatusCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private static final String CONFERENCE_ATTRIBUTE = "conference";
    private final ConferenceService conferenceService;

    public UpdateConferenceCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.traceEntry();
        Long conferenceId = parseIdParameter(request, CONFERENCE_ID_PARAM);
        if (conferenceId == null) {
            logger.debug("Failed to update conference. Incorrect conference id");
            return processBadRequest(request, response);
        }
        try {
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute(CONFERENCE_ATTRIBUTE, conference);
            return "conference-form";
        } catch (EntityNotFoundException e) {
            logger.debug("Failed to update conference id='{}'", conferenceId, e);
            return processPageNotFound(request, response);
        } catch (ServiceException e) {
            logger.error("Internal error. Failed to update conference id='{}'", conferenceId, e);
            return processInternalError(request, response);
        }
    }
}
