package by.epam.tc.conference.dao;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import java.util.List;

public interface ProposalDao extends GenericDao<Proposal> {

    List<Proposal> findBySectionId(Long id) throws DaoException;

    List<Proposal> findByUserId(Long id) throws DaoException;

    void updateStatus(Long id, ProposalStatus status) throws DaoException;
}
