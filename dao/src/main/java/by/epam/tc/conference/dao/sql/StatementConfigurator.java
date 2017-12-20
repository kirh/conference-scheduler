package by.epam.tc.conference.dao.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementConfigurator<T> {

    void configure(T entity, PreparedStatement preparedStatement) throws SQLException;
}
