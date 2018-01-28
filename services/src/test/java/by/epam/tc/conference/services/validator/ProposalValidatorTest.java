package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Proposal;
import by.epam.tc.conference.entity.ProposalStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ProposalValidatorTest {

    private Proposal proposal;

    private ProposalValidator validator = new ProposalValidator();

    @Before
    public void setUp() throws Exception {
        proposal = new Proposal();
        proposal.setId(1L);
        proposal.setParticipantId(1L);
        proposal.setStatus(ProposalStatus.PENDING);
        proposal.setSectionId(1L);
        proposal.setDescription("Valid description  Valid description  Valid description  Valid description" +
                "Valid description  Valid description  Valid description  Valid description  Valid description");
        proposal.setTitle("Proposal");
    }

    @Test
    public void shouldBeValidWhenValidProposalGiven() {
        boolean isValid = validator.validate(proposal);

        assertTrue(isValid);
    }

    @Test
    public void shouldBeValidWhenStatusNotSpecified() {
        proposal.setStatus(null);

        boolean isValid = validator.validate(proposal);

        assertTrue(isValid);
    }

    @Test
    public void shouldBeInvalidWhenDescriptionIsLessThenTwentyNonSpaceCharacters() {
        String description = String.format("%19s", " ").replace(" ", "A");
        proposal.setDescription(description);

        boolean isValid = validator.validate(proposal);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenNameIsLessThenFiveNonSpaceCharacters() {
        proposal.setTitle("som p");

        boolean isValid = validator.validate(proposal);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenParticipantIdIsNull() {
        proposal.setParticipantId(null);

        boolean isValid = validator.validate(proposal);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenParticipantIdIsBelowOne() {
        proposal.setParticipantId(0L);

        boolean isValid = validator.validate(proposal);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenSectionIdIsNull() {
        proposal.setSectionId(null);

        boolean isValid = validator.validate(proposal);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenSectionIdIsBelowOne() {
        proposal.setSectionId(0L);

        boolean isValid = validator.validate(proposal);

        assertFalse(isValid);
    }
}