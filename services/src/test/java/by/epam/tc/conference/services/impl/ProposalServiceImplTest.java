package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDao;
import by.epam.tc.conference.dao.UserDao;
import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import by.epam.tc.conference.entity.User;
import by.epam.tc.conference.services.exception.InvalidDataException;
import by.epam.tc.conference.services.exception.NoAuthorityException;
import by.epam.tc.conference.services.exception.NotFoundException;
import by.epam.tc.conference.services.exception.ServiceException;
import by.epam.tc.conference.services.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProposalServiceImplTest {

    @Mock
    private ProposalDao proposalDao;

    @Mock
    private UserDao userDao;

    @Mock
    private Validator<Proposal> validator;

    @InjectMocks
    ProposalServiceImpl proposalService;

    private Proposal proposal;

    @Before
    public void setUp() throws Exception {
        proposal = new Proposal();
    }

    @Test(expected = InvalidDataException.class)
    public void shouldNotCreateProposanOnCreateWhenProposalInvalid() throws ServiceException, DaoException {
        when(validator.validate(proposal)).thenReturn(false);

        proposalService.createProposal(proposal);

        verify(proposalDao, never()).save(proposal);
    }

    @Test
    public void shouldSaveProposalOnCreateWhenValidProposalGiven() throws ServiceException, DaoException {
        when(validator.validate(proposal)).thenReturn(true);

        proposalService.createProposal(proposal);

        verify(proposalDao).save(proposal);
    }

    @Test(expected = NoAuthorityException.class)
    public void shouldNotDeteleProposalWhenUserIsNotTheOwner() throws DaoException, ServiceException {
        proposal.setParticipantId(1L);
        when(proposalDao.findById(1L)).thenReturn(Optional.of(proposal));

        proposalService.deleteProposal(1L, 2L);

        verify(proposalDao, never()).deleteById(1L);
    }

    @Test
    public void shouldDeleteProposalWhenIdGiven() throws DaoException, ServiceException {
        proposal.setParticipantId(2L);
        when(proposalDao.findById(1L)).thenReturn(Optional.of(proposal));

        proposalService.deleteProposal(1L, 2L);

        verify(proposalDao).deleteById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void shouldBeExceptionOnGetProposalWhenProposalWithSpecifiedIdNotFound() throws ServiceException, DaoException {
        when(proposalDao.findById(1L)).thenReturn(Optional.empty());

        proposalService.getProposal(1L);
    }

    @Test
    public void shouldBeProposalWhenGetProposal() throws ServiceException, DaoException {
        when(proposalDao.findById(1L)).thenReturn(Optional.of(proposal));

        Proposal actual = proposalService.getProposal(1L);

        assertThat(actual, is(sameInstance(proposal)));
    }

    @Test(expected = NoAuthorityException.class)
    public void shouldNotUpdateProposalStatusWhenUserNotConferenceAdministrator() throws ServiceException, DaoException {
        User user = new User();
        user.setId(1L);
        when(userDao.findAdministratorByProposalId(1L)).thenReturn(Optional.of(user));

        proposalService.updateStatus(1L, ProposalStatus.APPROVED, 2L);

        verify(proposalDao, never()).updateStatus(any(), any());
    }

    @Test
    public void shouldUpdateStatusWhenUserIsCurrentConferenceAdministrator() throws DaoException, ServiceException {
        User user = new User();
        user.setId(1L);
        when(userDao.findAdministratorByProposalId(1L)).thenReturn(Optional.of(user));

        proposalService.updateStatus(1L, ProposalStatus.APPROVED, 1L);

        verify(proposalDao).updateStatus(1L, ProposalStatus.APPROVED);
    }
}