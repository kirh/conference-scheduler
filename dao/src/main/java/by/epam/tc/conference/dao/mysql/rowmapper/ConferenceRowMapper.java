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
    public Conference map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String description = resultSet.getString("c_description");
        String address = resultSet.getString(ADDRESS);
        Date date = resultSet.getDate(DATE);
        long administratorId = resultSet.getLong(ADMINISTRATOR_ID);
        return new Conference(id, name, description, address, date, administratorId);
    }
}
