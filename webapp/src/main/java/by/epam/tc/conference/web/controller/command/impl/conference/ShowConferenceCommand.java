package by.epam.tc.conference.web.controller.command.impl.conference;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.EntityNotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowConferenceCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ShowConferenceCommand.class);
    private static final String CONFERENCE_ID_PARAM = "id";
    private static final String CONFERENCE_ATTRIBUTE = "conference";
    private static final String SECTIONS_ATTRIBUTE = "sections";
    private final ConferenceService conferenceService;
    private final SectionService sectionService;

    public ShowConferenceCommand(ConferenceService conferenceService, SectionService sectionService) {
        this.conferenceService = conferenceService;
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long conferenceId = parseIdParameter(request, CONFERENCE_ID_PARAM);
        if (conferenceId == null) {
            logger.debug("Failed to show conference. Incorrect conference id");
            return processBadRequest(request, response);
        }
        String query;
        try {
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute(CONFERENCE_ATTRIBUTE, conference);
            List<Section> sections = sectionService.findSectionsByConferenceId(conferenceId);
            request.setAttribute(SECTIONS_ATTRIBUTE, sections);
            query = "conference";
        } catch (EntityNotFoundException e) {
            logger.debug("Failed to show conference id={}", conferenceId, e);
            query = processPageNotFound(request, response);
        } catch (ServiceException e) {
            logger.error("Internal error. Failed to show conference id={}", conferenceId, e);
            query = processInternalError(request, response);
        }
        return query;
    }
}
