package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Conference;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceRowMapper implements RowMapper<Conference> {

    private static final String ID = "c_id";
    private static final String NAME = "c_name";
    private static final String ADDRESS = "c_address";
    private static final String ADMINISTRATOR_ID = "c_u_id";
    private static final String DATE = "c_datetime";

    @Override
    public Conference handle(ResultSet resultSet) throws SQLException {
        Conference conference = new Conference();

        long id = resultSet.getLong(ID);
        conference.setId(id);

        String name = resultSet.getString(NAME);
        conference.setName(name);

        String description = resultSet.getString("c_description");
        conference.setDescription(description);

        String address = resultSet.getString(ADDRESS);
        conference.setAddress(address);

        long administratorId = resultSet.getLong(ADMINISTRATOR_ID);
        conference.setAdministratorId(administratorId);

        Date date = resultSet.getDate(DATE);
        conference.setDate(date);

        return conference;
    }
}
