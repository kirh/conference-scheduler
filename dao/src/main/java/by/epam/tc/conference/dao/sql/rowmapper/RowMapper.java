package by.epam.tc.conference.dao.sql.rowmapper;

import by.epam.tc.conference.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T extends Identifiable> {

    T handle(ResultSet resultSet) throws SQLException;
}
