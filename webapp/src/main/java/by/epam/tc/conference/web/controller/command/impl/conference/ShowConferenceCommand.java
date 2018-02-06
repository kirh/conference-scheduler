package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Adds conference with specified id to request and returns conference view
 */
public class ShowConferenceCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowConferenceCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private static final String CONFERENCE_ATTRIBUTE = "conference";
    private static final String SECTIONS_ATTRIBUTE = "sections";
    private static final String CONFERENCE_VIEW = "conference";

    private final ConferenceService conferenceService;
    private final SectionService sectionService;

    public ShowConferenceCommand(ConferenceService conferenceService, SectionService sectionService) {
        this.conferenceService = conferenceService;
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long conferenceId = parseIdParameter(request, CONFERENCE_ID_PARAM);
            logger.debug("Show conference id={}", conferenceId);
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute(CONFERENCE_ATTRIBUTE, conference);
            List<Section> sections = sectionService.findSectionsByConferenceId(conferenceId);
            request.setAttribute(SECTIONS_ATTRIBUTE, sections);
            return CONFERENCE_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to show conference");
        }
    }
}
