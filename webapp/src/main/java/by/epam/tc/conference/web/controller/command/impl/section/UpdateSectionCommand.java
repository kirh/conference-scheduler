package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateSectionCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(UpdateSectionCommand.class);
    private static final String SECTION_ID_PARAM = "id";
    private static final String SECTION_FORM_VIEW = "section-form";
    private static final String SECTION_ATTRIBUTE = "section";

    private final SectionService sectionService;

    public UpdateSectionCommand(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long sectionId = parseIdParameter(request, SECTION_ID_PARAM);
            logger.debug("Loading edit page for section id={}", sectionId);
            Section section = sectionService.getSection(sectionId);
            request.setAttribute(SECTION_ATTRIBUTE, section);
            return SECTION_FORM_VIEW;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to load edit page");
        }
    }
}
