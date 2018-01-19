package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Section;

import javax.servlet.http.HttpServletRequest;

public class SectionBuilder extends AbstractBuilder<Section> {

    @Override
    public Section build(HttpServletRequest request) {
        String idString = request.getParameter("id");
        Long id = parseId(idString);
        String topic = request.getParameter("topic");
        String conferenceIdString = request.getParameter("conferenceId");
        Long conferenceId = parseId(conferenceIdString);
        return new Section(id, topic, conferenceId);
    }
}
