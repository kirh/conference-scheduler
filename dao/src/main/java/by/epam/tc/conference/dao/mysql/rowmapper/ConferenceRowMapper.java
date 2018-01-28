package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Conference;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceRowMapper implements RowMapper<Conference> {

    private static final String ID_COLUMN = "c_id";
    private static final String NAME_COLUMN = "c_name";
    private static final String ADDRESS_COLUMN = "c_address";
    private static final String ADMINISTRATOR_ID_COLUMN = "c_u_id";
    private static final String DATE_COLUMN = "c_datetime";
    private static final String DESCRIPTION_COLUMN = "c_description";

    @Override
    public Conference map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        String address = resultSet.getString(ADDRESS_COLUMN);
        Date date = resultSet.getDate(DATE_COLUMN);
        long administratorId = resultSet.getLong(ADMINISTRATOR_ID_COLUMN);
        return new Conference(id, name, description, address, date, administratorId);
    }
}
