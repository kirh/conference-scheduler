package by.epam.tc.conference.dao.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Handler<T> {

    T handle(ResultSet resultSet) throws SQLException;
}
