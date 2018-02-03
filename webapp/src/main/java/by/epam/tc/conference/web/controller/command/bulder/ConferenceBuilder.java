package by.epam.tc.conference.web.controller.command.bulder;

import by.epam.tc.conference.entity.Conference;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class ConferenceBuilder extends AbstractBuilder<Conference> {

    private static final String CONFERENCE_ID_PARAM = "id";
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String ADDRESS_PARAM = "address";
    private static final String DATETIME_PARAM = "datetime";

    public Conference build(HttpServletRequest request) {
        String name = request.getParameter(NAME_PARAM);
        String description = request.getParameter(DESCRIPTION_PARAM);
        String address = request.getParameter(ADDRESS_PARAM);
        String idString = request.getParameter(CONFERENCE_ID_PARAM);
        Long id = parseId(idString);
        Long userId = getUserId(request);
        String dateString = request.getParameter(DATETIME_PARAM);
        Date date = java.sql.Date.valueOf(dateString);
        return new Conference(id, name, description, address, date, userId);
    }

}
