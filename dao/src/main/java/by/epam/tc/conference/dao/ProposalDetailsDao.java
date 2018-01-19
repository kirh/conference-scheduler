package by.epam.tc.conference.dao;

import by.epam.tc.conference.dto.ProposalDetails;

import java.util.List;

public interface ProposalDetailsDao {

    List<ProposalDetails> findProposalsByUserId(Long id) throws DaoException;

    List<ProposalDetails> findProposalsBySectionId(Long id) throws DaoException;

}
