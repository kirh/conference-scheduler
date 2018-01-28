package by.epam.tc.conference.dao.mysql.rowmapper;

import by.epam.tc.conference.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains logic for mapping ResultSet row to identifiable object
 *
 * @param <T> the type of result object
 */

@FunctionalInterface
public interface RowMapper<T extends Identifiable> {

    /**
     * Maps ResultSet row to identifiable object
     * @param resultSet with cursor in a row containing object parameters to map
     * @return result
     * @throws SQLException if the columnLabel is not valid; if a database access error
     * occurs or this method is called on a closed result set
     */

    T map(ResultSet resultSet) throws SQLException;
}
