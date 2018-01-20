package by.epam.tc.conference.web.controller.command.impl.section;

import by.epam.tc.conference.services.SectionService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSectionCommand extends AbstractCommand {
    private final SectionService sectionService;

    public DeleteSectionCommand(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
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
