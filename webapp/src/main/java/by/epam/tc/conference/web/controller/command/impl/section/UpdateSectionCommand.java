package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateSectionCommand extends AbstractCommand {

    private static final String SECTION_ID_PARAM = "id";
    private final SectionService sectionService;

    public UpdateSectionCommand(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long id = parseIdParameter(request, SECTION_ID_PARAM);
        if (id == null) {
            return processBadRequest(request, response);
        }
        try {
            Section section = sectionService.getSection(id);
            request.setAttribute("section", section);
            return "section-form";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
