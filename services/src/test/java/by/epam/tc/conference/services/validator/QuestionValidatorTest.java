package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QuestionValidatorTest {

    private Question question;

    private QuestionValidator validator = new QuestionValidator();

    @Before
    public void setUp() throws Exception {
        question = new Question();
        question.setId(1L);
        question.setTitle("some title");
        question.setConferenceId(1L);
        question.setUserId(1L);
    }

    @Test
    public void shouldBeValidWhenValidQuestionGiven() {
        boolean isValid = validator.validate(question);

        assertTrue(isValid);
    }

    @Test
    public void shouldBeInvalidWhenTitleIsLessThenFiveNonSpaceCharacters() {
        question.setTitle("a s d f");

        boolean isValid = validator.validate(question);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenIdBelowOne() {
        question.setId(0L);

        boolean isValid = validator.validate(question);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenUserIdIsNull() {
        question.setUserId(null);

        boolean isValid = validator.validate(question);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenConferenceIdIsNull() {
        question.setConferenceId(null);

        boolean isValid = validator.validate(question);

        assertFalse(isValid);
    }
}