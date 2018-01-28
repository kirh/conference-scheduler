package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.helper.Builder;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import by.epam.tc.conference.web.controller.command.impl.conference.ProcessConferenceCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProcessProposalCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger(ProcessConferenceCommand.class);
    private static final String REDIRECT_PARTICIPANT_DASHBOARD_QUERY = "redirect:/user-dashboard";
    private final ProposalService proposalService;
    private final Builder<? extends Proposal> builder;

    public ProcessProposalCommand(ProposalService proposalService, Builder<? extends Proposal> builder) {
        this.proposalService = proposalService;
        this.builder = builder;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            logger.debug("Processing proposal");
            Proposal proposal = builder.build(request);
            if (proposal.getId() == null) {
                proposalService.createProposal(proposal);
            } else {
                Long userId = getUserId(request);
                proposalService.updateProposal(proposal, userId);
            }
            return REDIRECT_PARTICIPANT_DASHBOARD_QUERY;
        } catch (ServiceException e) {
            throw CommandException.from(e, "Failed to process proposal");
        }
    }
}
