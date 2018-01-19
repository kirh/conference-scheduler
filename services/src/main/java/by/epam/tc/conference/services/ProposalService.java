package by.epam.tc.conference.services;

import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;

import java.util.List;

public interface ProposalService {

    void createProposal(Proposal proposal) throws ServiceException;

    void deleteProposal(Long id) throws ServiceException;

    List<ProposalDetails> findProposalsDetailsBySectionId(Long id) throws ServiceException;

    List<ProposalDetails> findProposalsDetailsByParticipantId(Long id) throws ServiceException;

    Proposal getProposal(Long id) throws ServiceException;

    void updateStatus(Long id, ProposalStatus status) throws ServiceException;

    void updateProposal(Proposal proposal) throws ServiceException;


}
