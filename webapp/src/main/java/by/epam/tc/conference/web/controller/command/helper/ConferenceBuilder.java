package by.epam.tc.conference.web.controller.command.helper;

import by.epam.tc.conference.entity.Conference;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class ConferenceBuilder extends AbstractBuilder<Conference> {

    private static final String CONFERENCE_ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String ADDRESS = "address";
    private static final String DATETIME = "datetime";

    public Conference build(HttpServletRequest request) {
        String idString = request.getParameter(CONFERENCE_ID);
        Long id = parseId(idString);
        String name = request.getParameter(NAME);
        String description = request.getParameter(DESCRIPTION);
        String address = request.getParameter(ADDRESS);
        Long userId = getUserId(request);
        String dateString = request.getParameter(DATETIME);
        Date date = java.sql.Date.valueOf(dateString);
        return new Conference(id, name, description, address, date, userId);
    }
}
