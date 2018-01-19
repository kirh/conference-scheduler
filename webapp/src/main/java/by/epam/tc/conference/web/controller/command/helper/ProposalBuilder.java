package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import javax.servlet.http.HttpServletRequest;

public class ProposalBuilder extends AbstractBuilder<Proposal> {

    private static final String PARAM_ID = "id";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_SECTION_ID = "sectionId";

    @Override
    public Proposal build(HttpServletRequest request) {
        String idString = request.getParameter(PARAM_ID);
        Long id = parseId(idString);
        String title = request.getParameter(PARAM_TITLE);
        String description = request.getParameter(PARAM_DESCRIPTION);
        String sectionIdString = request.getParameter(PARAM_SECTION_ID);
        Long sectionId = parseId(sectionIdString);
        Long participantId = getUserId(request);
        return new Proposal(id, title, description, sectionId, participantId, ProposalStatus.PENDING);
    }
}
