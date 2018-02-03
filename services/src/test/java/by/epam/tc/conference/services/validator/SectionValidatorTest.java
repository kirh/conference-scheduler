package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Section;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SectionValidatorTest {

    private SectionValidator validator = new SectionValidator();

    private Section section;

    @Before
    public void setUp() throws Exception {
        section = new Section();
        section.setId(1L);
        section.setConferenceId(1L);
        section.setTopic("Topic");
    }

    @Test
    public void shouldBeValidWhenValidSectionGiven() {
        boolean isValid = validator.validate(section);

        assertTrue(isValid);
    }

    @Test
    public void shouldBeInvalidWhenTopicContainsLessThenFiveLetters() {
        section.setTopic("a b c d");

        boolean isValid = validator.validate(section);
        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenConferenceIdIsNull() {
        section.setConferenceId(null);

        boolean isValid = validator.validate(section);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenConferenceIdBelowOne() {
        section.setConferenceId(0L);

        boolean isValid = validator.validate(section);

        assertFalse(isValid);
    }
}