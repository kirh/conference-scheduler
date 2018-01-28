package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Question;

import java.util.regex.Pattern;

/**
 * Logic to validate {@link Question}
 * Implements {@link Validator}
 */
public class QuestionValidator extends AbstractValidator<Question> {

    private static final Pattern AT_LEAST_FIVE_LETTERS_PATTERN = Pattern.compile("(?:\\S\\s?){5,}");

    @Override
    public boolean validate(Question question) {
        if (question == null) {
            return false;
        }

        Long id = question.getId();
        if (id != null && id <= 0) {
            return false;
        }

        Long conferenceId = question.getConferenceId();
        Long userId = question.getUserId();
        if (!isValidId(conferenceId) || !isValidId(userId)) {
            return false;
        }

        String title = question.getTitle();
        return isMatches(title, AT_LEAST_FIVE_LETTERS_PATTERN);
    }
}
