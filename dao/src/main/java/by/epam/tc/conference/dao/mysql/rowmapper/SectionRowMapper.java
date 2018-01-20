package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Section;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionRowMapper implements RowMapper<Section> {

    private static final String ID = "s_id";
    private static final String TOPIC = "s_topic";
    private static final String CONFERENCE_ID = "s_c_id";

    @Override
    public Section map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String topic = resultSet.getString(TOPIC);
        long conferenceId = resultSet.getLong(CONFERENCE_ID);
        return new Section(id, topic, conferenceId);
    }
}
