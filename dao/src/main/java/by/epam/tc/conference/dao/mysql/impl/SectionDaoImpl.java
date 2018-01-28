package by.epam.tc.conference.dao.mysql.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.SectionDao;
import by.epam.tc.conference.dao.mysql.Executor;
import by.epam.tc.conference.entity.Section;

import java.util.List;

public class SectionDaoImpl extends AbstractDao<Section> implements SectionDao {

    private static final String UPDATE = "update section set s_topic=?, s_c_id=? where s_id=?";
    private static final String DELETE = "delete from section where s_id=?";
    private static final String SELECT = "select * from section where s_id=?";
    private static final String SELECT_ALL = "select * from section";
    private static final String SAVE = "insert into section (s_topic, s_c_id) values(?, ?)";
    private static final String SELECT_BY_SECTION_ID = "select * from section where s_c_id=?";
    private static final String SELECT_ALL_PAGE = "select * from conference limit ?,? order by s_id";

    public SectionDaoImpl(Executor<Section> executor) {
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
    protected Object[] getSaveParameters(Section section) {
        return new Object[]{
                section.getTopic(),
                section.getConferenceId()
        };
    }

    @Override
    protected Object[] getUpdateParameters(Section section) {
        return new Object[]{
                section.getTopic(),
                section.getConferenceId(),
                section.getId()
        };
    }

    @Override
    public List<Section> findSectionsByConferenceId(long id) throws DaoException {
        return executor.executeAndFetchAll(SELECT_BY_SECTION_ID, id);
    }
}
