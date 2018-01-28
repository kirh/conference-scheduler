package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Conference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceValidatorTest {

    private Conference conference;

    private ConferenceValidator validator = new ConferenceValidator();

    @Before
    public void setUp() throws Exception {
        conference = new Conference();
        conference.setId(1L);
        conference.setAdministratorId(1L);
        conference.setDescription("Valid description  Valid description  Valid description  Valid description" +
                "Valid description  Valid description  Valid description  Valid description  Valid description");
        conference.setDate(new Date(2100, 12, 12));
        conference.setAddress("Moscow somewhere");
        conference.setName("Conference");
    }

    @Test
    public void shouldBeValidWhenValidConferenceGiven() {
        ConferenceValidator validator = new ConferenceValidator();

        boolean isValid = validator.validate(conference);
        assertTrue(isValid);
    }

    @Test
    public void shouldBeInvalidWhenDescriptionContainLessThenTwentyLetters() {
        String nineteenLength = String.format("%19s", " ").replace(" ", "A");
        conference.setDescription(nineteenLength);

        boolean isValid = validator.validate(conference);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenNameContainLessThenFiveLetters() {
        conference.setName("d f f f");

        boolean isValid = validator.validate(conference);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenAddressContainLessThenFiveLetters() {
        conference.setAddress("d f f f");

        boolean isValid = validator.validate(conference);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenAdminIdIsBelowOne() {
        conference.setAdministratorId(0L);

        boolean isValid = validator.validate(conference);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenAdminIdIsNull() {
        conference.setAdministratorId(null);

        boolean isValid = validator.validate(conference);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenOutDated() throws ParseException {
        Date past = new Date("Sat, 12 Aug 1995 13:30:00 GMT");
        conference.setDate(past);

        boolean isValid = validator.validate(conference);

        assertFalse(isValid);
    }
}