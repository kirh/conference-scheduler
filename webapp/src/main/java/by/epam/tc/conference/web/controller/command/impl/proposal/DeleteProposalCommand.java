package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProposalCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(DeleteProposalCommand.class);
    private final ProposalService proposalService;

    public DeleteProposalCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idString = request.getParameter("id");
        Long id = Long.valueOf(idString);
        try {
            proposalService.deleteProposal(id);
            return "redirect:";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
