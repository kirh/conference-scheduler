package by.epam.tc.conference.dao.sql.rowmapper;

import by.epam.tc.conference.entity.Section;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionRowMapper implements RowMapper<Section> {

    private static final String ID = "s_id";
    private static final String TOPIC = "s_topic";
    private static final String CONFERENCE_ID = "s_c_id";

    @Override
    public Section handle(ResultSet resultSet) throws SQLException {
        Section section = new Section();

        long id = resultSet.getLong(ID);
        section.setId(id);

        String topic = resultSet.getString(TOPIC);
        section.setTopic(topic);

        long conferenceId = resultSet.getLong(CONFERENCE_ID);
        section.setConferenceId(conferenceId);

        return section;
    }
}
