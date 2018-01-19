package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.Conference;
import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ConferenceService;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.ConferenceBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class ConferenceCommand extends AbstractCommand {

    private static final String CONFERENCE_ID_PARAM = "id";
    private final ConferenceBuilder conferenceBuilder;
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions;

    public ConferenceCommand(ConferenceBuilder conferenceBuilder, ConferenceService conferenceService, SectionService
            sectionService) {
        this.conferenceBuilder = conferenceBuilder;
        this.conferenceService = conferenceService;
        this.sectionService = sectionService;

        Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> map = new HashMap<>();
        map.put("add", (request, response) -> "conference-form");
        map.put("delete", this::deleteConference);
        map.put("update", this::updateConference);
        map.put("show", this::showConference);
        map.put("showAll", this::showAllConferences);
        map.put("process", this::processConference);
        actions = Collections.unmodifiableMap(map);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String actionParameter = request.getParameter("action");
        BiFunction<HttpServletRequest, HttpServletResponse, String> action = actions.get(actionParameter);
        if (action == null) {
            return processPageNotFound(request, response);
        }
        return action.apply(request, response);
    }

    private String showConference(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter(CONFERENCE_ID_PARAM);
        if (idString == null) {
            return processPageNotFound(request, response);
        }
            String query;
            try {
                Long conferenceId = Long.valueOf(idString);
                Conference conference = conferenceService.getConference(conferenceId);
                request.setAttribute("conference", conference);
                List<Section> sections = sectionService.findSectionsByConferenceId(conferenceId);
                request.setAttribute("sections", sections);
                query = "conference";
            } catch (ServiceException e) {
                query = processInternalError(request, response);
            }
        return query;
    }

    private String showAllConferences(HttpServletRequest request, HttpServletResponse response) {
        String query;
        try {
            List<Conference> conferences = conferenceService.getAllConferences();
            request.setAttribute("conferences", conferences);
            query = "conferences";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }

    private String deleteConference(HttpServletRequest request, HttpServletResponse response) {
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
        return query;
    }

    private String updateConference(HttpServletRequest request, HttpServletResponse response) {
        String conferenceIdString = request.getParameter(CONFERENCE_ID_PARAM);
        if (conferenceIdString == null) {
            return processInternalError(request, response);
        }
        try {
            Long conferenceId = Long.valueOf(conferenceIdString);
            Conference conference = conferenceService.getConference(conferenceId);
            request.setAttribute("conference", conference);
            return "conference-form";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }

    private String processConference(HttpServletRequest request, HttpServletResponse response) {
        Conference conference = conferenceBuilder.build(request);
        try {
            Long conferenceId = conference.getId();
            if (conferenceId == null) {
                conferenceService.createConference(conference);
            } else {
                conferenceService.updateConference(conference);
            }
            return "redirect:/admin-dashboard";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
