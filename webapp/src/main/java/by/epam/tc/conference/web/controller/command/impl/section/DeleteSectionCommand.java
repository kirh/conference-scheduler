package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.Dispatcher;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSectionCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(DeleteSectionCommand.class);
    private static final String SECTION_ID_PARAM = "id";

    private final SectionService sectionService;

    public DeleteSectionCommand(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            Long sectionId = parseIdParameter(request, SECTION_ID_PARAM);
            logger.debug("Deleting section id={}", sectionId);
            Long userId = getUserId(request);
            sectionService.deleteSection(sectionId, userId);
            return Dispatcher.SKIP;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to delete section");
        }
    }
}
