package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessProposalCommand extends AbstractCommand {
    private final ProposalService proposalService;
    private final Builder<? extends Proposal> builder;

    public ProcessProposalCommand(ProposalService proposalService, Builder<? extends Proposal> builder) {
        this.proposalService = proposalService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Proposal proposal = builder.build(request);
        try {
            if (proposal.getId() == null) {
                proposalService.createProposal(proposal);
            } else {
                proposalService.updateProposal(proposal);
            }
            return "redirect:/user-dashboard";
        } catch (ServiceException e) {
            return processInternalError(request, response);
        }
    }
}
