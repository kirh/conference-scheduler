package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessSectionCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessSectionCommand.class);
    private final Builder<? extends Section> builder;
    private final SectionService sectionService;

    public ProcessSectionCommand(Builder<? extends Section> builder, SectionService sectionService) {
        this.builder = builder;
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            logger.debug("Processing section");
            Section section = builder.build(request);
            if (section.getId() == null) {
                sectionService.createSection(section);
            } else {
                Long userId = getUserId(request);
                sectionService.updateSection(section, userId);
            }
            Long conferenceId = section.getConferenceId();
            return "redirect:/conference?action=show&id=" + conferenceId;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to process section");
        }
    }
}
