package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.Command;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.SectionBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class SectionCommand extends AbstractCommand {


    private static final String CONFERENCE_ID_PARAM = "conferenceId";
    private final SectionService sectionService;
    private final Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions;

    public SectionCommand(SectionService sectionService) {
        this.sectionService = sectionService;

        Map<String, BiFunction<HttpServletRequest, HttpServletResponse, String>> actions = new HashMap<>();
        actions.put("show", this::showSections);
        actions.put("update", this::updateSection);
        actions.put("create", (request, response) -> "section-form");
        actions.put("process", this::processSection);
        actions.put("delete", this::deleteSection);
        this.actions = Collections.unmodifiableMap(actions);
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String actionParameter = request.getParameter("action");
        BiFunction<HttpServletRequest, HttpServletResponse, String> action = actions.get(actionParameter);
        if (action == null) {
            return processPageNotFound(request, response);
        }
        return action.apply(request, response);
    }

    private String showSections(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter(CONFERENCE_ID_PARAM);
        Long id = Long.valueOf(idString);
        String query;
        try {
            List<Section> sections = sectionService.findSectionsByConferenceId(id);
            request.setAttribute("sections", sections);
            query = "sections";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }

    private String processSection(HttpServletRequest request, HttpServletResponse response) {
        Section section = new SectionBuilder().build(request);

        try {
            if (section.getId() == null) {
                sectionService.createSection(section);
            } else {
                sectionService.updateSection(section);
            }
            return "redirect:conference?action=show&id=" + section.getConferenceId();
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }

    private String updateSection(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        Long id = Long.valueOf(idString);
        try {
            Section section = sectionService.getSection(id);
            request.setAttribute("section", section);
            return "section-form";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }

    private String deleteSection(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        Long id = Long.valueOf(idString);
        try {
            sectionService.deleteSection(id);
            return "redirect:";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
