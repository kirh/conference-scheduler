package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.User;

import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String UPDATE = "update user set u_username=?, u_password=?, u_email=?, u_first_name=? "
            + "u_last_name=? u_is_admin=? where u_id=?";
    private static final String DELETE = "delete from user where u_id=?";
    private static final String SELECT = "select u_id, u_username, u_password, u_email, u_first_name, u_last_name, "
            + "u_is_admin from user where u_id=?";
    private static final String SELECT_ALL = "select u_id, u_username, u_password, u_email, u_first_name, u_last_name, "
            + "u_is_admin from user";
    private static final String SAVE = "insert into user (u_username, u_password, u_email, u_first_name, "
            + "u_last_name, u_is_admin) values(?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME = "select u_id, u_username, u_password, u_email, u_first_name,"
            + " u_last_name, u_is_admin from user where u_username=?";
    private static final String SELECT_ALL_PAGE = "select u_id, u_username, u_password, u_email, u_first_name, u_last_name, u_is_admin from user limit ?,? order by u_id";
    private static final String SELECT_ADMINISTRATOR_BY_PROPOSAL = "select u_id, u_username, u_password, u_email, "
            + "u_first_name, u_last_name, u_is_admin from proposal join section on "
            + "p_s_id=s_id join conference on s_c_id=c_id join user on u_id=c_u_id where p_id=?";
    private static final String SELECT_ADMINISTRATOR_BY_SECTION = "select u_id, u_username, u_password, u_email, "
            + "u_first_name, u_last_name, u_is_admin from section join conference on "
            + "s_c_id=c_id join user on c_u_id=u_id where s_id=?";
    private static final String SELECT_ADMINISTRATOR_BY_QUESTION = "select u_id, u_username, u_password, u_email, "
            + "u_first_name, u_last_name, u_is_admin from question join conference on "
            + "q_c_id=c_id join user on c_u_id=u_id where q_id=?";

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
    public Optional<User> findByUsername(String username) throws DaoException {
        return executor.executeAndFetchOne(SELECT_BY_USERNAME, username);
    }

    @Override
    public Optional<User> findAdministratorByProposalId(long proposalId) throws DaoException {
        return executor.executeAndFetchOne(SELECT_ADMINISTRATOR_BY_PROPOSAL, proposalId);
    }

    @Override
    public Optional<User> findAdministratorBySectionId(long sectionId) throws DaoException {
        return executor.executeAndFetchOne(SELECT_ADMINISTRATOR_BY_SECTION, sectionId);
    }

    @Override
    public Optional<User> findAdministratorByQuestionId(long questionId) throws DaoException {
        return executor.executeAndFetchOne(SELECT_ADMINISTRATOR_BY_QUESTION, questionId);
    }
}
