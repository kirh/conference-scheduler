package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageValidatorTest {

    private Message message;

    private MessageValidator validator = new MessageValidator();

    @Before
    public void setUp() throws Exception {
        message = new Message();
        message.setQuestionId(1L);
        message.setUserId(1L);
        message.setId(1L);
        message.setText("message");
        message.setCreateTime(Timestamp.from(Instant.now()));
    }

    @Test
    public void shouldBeValidWhenValidMessageGiven() {
        boolean isValid = validator.validate(message);

        assertTrue(isValid);
    }

    @Test
    public void shouldBeInvalidWhenContainsLessThenFiveLetters() {
        message.setText("s s s s");

        boolean isValid = validator.validate(message);

        assertFalse(isValid);
    }
}