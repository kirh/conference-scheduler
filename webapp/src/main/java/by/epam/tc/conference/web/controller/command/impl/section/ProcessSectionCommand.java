package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessSectionCommand extends AbstractCommand {

    private final Builder<? extends Section> builder;
    private final SectionService sectionService;

    public ProcessSectionCommand(Builder<? extends Section> builder, SectionService sectionService) {
        this.builder = builder;
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Section section = builder.build(request);
        try {
            if (section.getId() == null) {
                sectionService.createSection(section);
            } else {
                sectionService.updateSection(section);
            }
            return "redirect:/conference?action=show&id=" + section.getConferenceId();
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
