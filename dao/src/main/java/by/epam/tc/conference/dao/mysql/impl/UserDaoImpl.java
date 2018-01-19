package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.User;

import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String UPDATE = "update user set u_username=?, u_password=?, u_email=?, u_first_name=? " +
            "u_last_name=? u_is_admin=? where u_id=?";
    private static final String DELETE = "delete from user where u_id=?";
    private static final String SELECT = "select * from user where u_id=?";
    private static final String SELECT_ALL = "select * from user";
    private static final String SAVE = "insert into user (u_username, u_password, u_email, u_first_name, " +
            "u_last_name, u_is_admin) values(?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME = "select * from user where u_username=?";
    private static final String SELECT_ALL_PAGE = "select * from user limit ?,? order by u_id";

    public UserDaoImpl(Executor<User> executor) {
        super(executor);
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
    protected String getSelectAllPageQuery() {
        return SELECT_ALL_PAGE;
    }

    @Override
    protected Object[] getSaveParameters(User user) {
        return new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isAdmin()
        };
    }

    @Override
    protected Object[] getUpdateParameters(User user) {
        return new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isAdmin(),
                user.getId()
        };
    }

    @Override
    public Optional<User> findByUserName(String username) throws DaoException {
        return executor.executeAndFetchOne(SELECT_BY_USERNAME, username);
    }
}
