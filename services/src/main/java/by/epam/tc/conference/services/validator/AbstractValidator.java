package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Identifiable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common logic for validators
 * @param <T>
 */
public abstract class AbstractValidator<T extends Identifiable> implements Validator<T> {

    /**
     * Checks text against given pattern
     *
     * @param text to check
     * @param pattern to match
     * @return true if text matches pattern
     */
    protected boolean isMatches(String text, Pattern pattern) {
        if (text == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * Validates given id value
     * @param id to validate
     * @return true when id is not null and greater then zero
     */
    protected boolean isValidId(Long id) {
        return id != null && id > 0;
    }
}
