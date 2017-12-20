package by.epam.tc.conference.dao.sql.object;

import by.epam.tc.conference.dao.sql.Executor;
import by.epam.tc.conference.dao.sql.ResultHandler;
import by.epam.tc.conference.dao.sql.object.AbstractDao;
import by.epam.tc.conference.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao extends AbstractDao<Long, User> {

    private static final String UPDATE = "update user set u_username=?, u_password=?, u_email=?, u_first_name=? " +
            "u_last_name=? u_is_admin=? where u_id=?";
    private static final String DELETE = "delete from user where u_id=?";
    private static final String SELECT = "select * from user where u_id=?";
    private static final String SELECT_ALL = "select * from user";
    private static final String SAVE = "insert into user (u_username, u_password, u_email, u_first_name, u_last_name," +
            " u_is_admin) values(?, ?, ?, ?, ?, ?)";

    public UserDao(ResultHandler<User> resultHandler, Executor<Long, User> executor) {
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
    protected void configureUpdateStatement(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getUsername());
        statement.setString(1, entity.getPassword());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getFirstName());
        statement.setString(4, entity.getLastName());
        statement.setBoolean(5, entity.isAdmin());
        statement.setLong(6, entity.getId());

    }

    @Override
    protected void configureSelectByIdStatement(Long id, PreparedStatement statement) throws SQLException {
        statement.setLong(0, id);
    }

    @Override
    protected void configureSaveStatement(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(0, entity.getUsername());
        statement.setString(1, entity.getPassword());
        statement.setString(2, entity.getEmail());
        statement.setString(3, entity.getFirstName());
        statement.setString(4, entity.getLastName());
        statement.setBoolean(5, entity.isAdmin());
    }

    @Override
    protected void configureDeleteStatement(User entity, PreparedStatement statement) throws SQLException {
        statement.setLong(0, entity.getId());
    }
}
