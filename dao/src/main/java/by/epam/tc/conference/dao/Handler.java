package by.epam.tc.conference.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Handler<T> {

    public T handle(ResultSet resultSet) throws SQLException;
}
