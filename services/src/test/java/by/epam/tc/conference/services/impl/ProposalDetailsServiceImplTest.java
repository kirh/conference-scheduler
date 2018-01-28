package by.epam.tc.conference.services.impl;

import by.epam.tc.conference.dao.DaoException;
import by.epam.tc.conference.dao.ProposalDetailsDao;
import by.epam.tc.conference.dto.ProposalDetails;
import by.epam.tc.conference.services.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProposalDetailsServiceImplTest {

    @Mock
    private ProposalDetailsDao proposalDetailsDao;

    @InjectMocks
    private ProposalDetailsServiceImpl proposalDetailsService;

    @Test
    public void shouldBeProposalsWhenFindBySectionId() throws ServiceException, DaoException {
        List<ProposalDetails> proposals = Collections.emptyList();
        when(proposalDetailsDao.findProposalsBySectionId(1L)).thenReturn(proposals);

        List<ProposalDetails> actual = proposalDetailsService.findProposalsDetailsBySectionId(1L);

        assertThat(actual, is(sameInstance(proposals)));
    }

    @Test
    public void shouldBeProposalsWhenFindByParticipantId() throws DaoException, ServiceException {
        List<ProposalDetails> proposals = Collections.emptyList();
        when(proposalDetailsDao.findProposalsByUserId(1L)).thenReturn(proposals);

        List<ProposalDetails> actual = proposalDetailsService.findProposalsDetailsByParticipantId(1L);

        assertThat(actual, is(sameInstance(proposals)));
    }
}