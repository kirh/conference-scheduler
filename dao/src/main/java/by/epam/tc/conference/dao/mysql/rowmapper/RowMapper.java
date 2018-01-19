package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents logic for mapping ResultSet row to identifiable object
 *
 * @param <T> the type of result object
 */

@FunctionalInterface
public interface RowMapper<T extends Identifiable> {

    /**
     *
     * @param resultSet with cursor in a row containing object parameters to map
     * @return result
     * @throws SQLException
     */

    T handle(ResultSet resultSet) throws SQLException;
}
