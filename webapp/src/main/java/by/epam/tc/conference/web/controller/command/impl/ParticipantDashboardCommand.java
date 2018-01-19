package by.epam.tc.conference.web.controller.command.impl;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.UserPrincipal;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ParticipantDashboardCommand extends AbstractCommand {

    private final ProposalService proposalService;

    public ParticipantDashboardCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserPrincipal user = getUser(request);
        if (user.isAdmin()) {
            return forbidRequest(request, response);
        }
        String query;
        Long userId = user.getId();
        try {
            List<ProposalDetails> proposals = proposalService.findProposalsDetailsByParticipantId(userId);
            request.setAttribute("proposals", proposals);
            query = "user-dashboard";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }
}
