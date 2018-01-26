package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.entity.Section;
import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.InvalidEntityException;
import by.epam.tc.conference.services.exception.ServiceException;
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
            logger.error("Failed to process section", e);
            return processInternalError(request, response);
        } catch (InvalidEntityException e) {
            logger.debug("Failed to process section", e);
            return processBadRequest(request, response);
        }
    }
}
