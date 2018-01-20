package by.epam.tc.conference.web.controller.command.impl.proposal;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.ProposalService;
import by.epam.tc.conference.services.ServiceException;
import by.epam.tc.conference.web.controller.command.CommandException;
import by.epam.tc.conference.web.controller.command.impl.AbstractCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllProposalsCommand extends AbstractCommand {
    private static final Logger logger = LogManager.getLogger(ShowAllProposalsCommand.class);
    private static final String SECTION_ID_PARAM = "sectionId";
    private final ProposalService proposalService;

    public ShowAllProposalsCommand(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idString = request.getParameter(SECTION_ID_PARAM);
        Long id = Long.valueOf(idString);
        String query;
        try {
            List<ProposalDetails> proposals = proposalService.findProposalsDetailsBySectionId(id);
            request.setAttribute("proposals", proposals);
            query = "section";
        } catch (ServiceException e) {
            query = processInternalError(request, response);
        }
        return query;
    }
}
