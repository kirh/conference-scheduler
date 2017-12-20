package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.StatementConfigurator;
import by.epam.tc.conference.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements by.epam.tc.conference.dao.sql.UserDao {

    private static final String UPDATE = "update user set u_username=?, u_password=?, u_email=?, u_first_name=? " +
            "u_last_name=? u_is_admin=? where u_id=?";
    private static final String DELETE = "delete from user where u_id=?";
    private static final String SELECT = "select * from user where u_id=?";
    private static final String SELECT_ALL = "select * from user";
    private static final String SAVE = "insert into user (u_username, u_password, u_email, u_first_name, u_last_name," +
            " u_is_admin) values(?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME = "select * from user where u_username=? and u_password=md5(?)";

    public UserDaoImpl(ResultHandler<User> resultHandler, Executor<User> executor) {
        super(resultHandler, executor);
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getSaveQuery() {
        return SAVE;
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT;
    }

    @Override
    protected void configureSaveStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(0, user.getUsername());
        statement.setString(1, user.getPassword());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setBoolean(5, user.isAdmin());
    }

    @Override
    protected void configureUpdateStatement(User user, PreparedStatement statement) throws SQLException {
        configureSaveStatement(user, statement);
        statement.setLong(6, user.getId());
    }

    @Override
    public Optional<User> findByUserNameAndPassword(String username, String password) throws DaoException {
        StatementConfigurator<Long> configurator =(user, statement) -> {
            statement.setString(1, username);
            statement.setString(2, password);
        };
        return executor.execute(configurator , SELECT_BY_USERNAME,
                resultHandler::fetchOne, null);
    }
}
