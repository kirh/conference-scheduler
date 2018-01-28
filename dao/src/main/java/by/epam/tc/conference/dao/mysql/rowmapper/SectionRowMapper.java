package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Section;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionRowMapper implements RowMapper<Section> {

    private static final String ID_COLUMN = "s_id";
    private static final String TOPIC_COLUMN = "s_topic";
    private static final String CONFERENCE_ID_COLUMN = "s_c_id";

    @Override
    public Section map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String topic = resultSet.getString(TOPIC_COLUMN);
        long conferenceId = resultSet.getLong(CONFERENCE_ID_COLUMN);
        return new Section(id, topic, conferenceId);
    }
}
