package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import by.epam.tc.conference.web.controller.command.impl.proposal.UpdateProposalStatusCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds conference with specified id to request attributes and returns conference form view for editing.
 */
public class UpdateConferenceCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(UpdateProposalStatusCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private static final String CONFERENCE_ATTRIBUTE = "conference";
    private static final String CONFERENCE_FORM_VIEW = "conference-form";

    private final ConferenceService conferenceService;

    public UpdateConferenceCommand(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long conferenceId = parseIdParameter(request, CONFERENCE_ID_PARAM);
            logger.debug("Loading edit page for conference id={}", conferenceId);
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute(CONFERENCE_ATTRIBUTE, conference);
            return CONFERENCE_FORM_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to load edit page");
        }
    }
}
