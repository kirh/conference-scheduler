package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import javax.servlet.http.HttpServletRequest;

public class ProposalBuilder extends AbstractBuilder<Proposal> {

    private static final String ID_PARAM = "id";
    private static final String TITLE_PARAM = "title";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String SECTION_ID_PARAM = "sectionId";

    @Override
    public Proposal build(HttpServletRequest request) {
        String idString = request.getParameter(ID_PARAM);
        Long id = parseId(idString);
        String title = request.getParameter(TITLE_PARAM);
        String description = request.getParameter(DESCRIPTION_PARAM);
        String sectionIdString = request.getParameter(SECTION_ID_PARAM);
        Long sectionId = parseId(sectionIdString);
        Long participantId = getUserId(request);
        return new Proposal(id, title, description, sectionId, participantId, ProposalStatus.PENDING);
    }
}
