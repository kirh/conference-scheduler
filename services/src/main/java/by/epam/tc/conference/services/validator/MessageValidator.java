package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

/**
 * Logic to validate {@link Message}
 * Implements {@link Validator}
 */
public class MessageValidator extends AbstractValidator<Message> {

    private static final Logger logger = LogManager.getLogger(MessageValidator.class);
    private static final Pattern AT_LEAST_FIVE_LETTERS_PATTERN = Pattern.compile("(?:\\S\\s*){5,}");

    @Override
    public boolean validate(Message message) {
        logger.debug("Validating message");

        if (message == null) {
            return false;
        }

        Long id = message.getId();
        if (id != null && id <= 0) {
            return false;
        }

        Long questionId = message.getQuestionId();
        Long userId = message.getUserId();
        if (!isValidId(questionId) || !isValidId(userId)) {
            return false;
        }

        String text = message.getText();
        return isMatches(text, AT_LEAST_FIVE_LETTERS_PATTERN);
    }
}
