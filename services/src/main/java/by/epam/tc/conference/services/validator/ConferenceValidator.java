package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Conference;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Logic to validate {@link Conference}
 * Implements {@link Validator}
 */
public class ConferenceValidator extends AbstractValidator<Conference> {

    private static final Pattern AT_LEAST_FIVE_LETTERS_PATTERN = Pattern.compile("(?:\\S\\s?){5,}");
    private static final Pattern AT_LEAST_TWENTY_LETTERS_PATTERN = Pattern.compile(".{20,}", Pattern.DOTALL);

    @Override
    public boolean validate(Conference conference) {
        if (conference == null) {
            return false;
        }

        Long id = conference.getId();
        if (id != null && id <= 0) {
            return false;
        }

        Long administratorId = conference.getAdministratorId();
        if (!isValidId(administratorId)) {
            return false;
        }

        String description = conference.getDescription();
        if (!isMatches(description, AT_LEAST_TWENTY_LETTERS_PATTERN)) {
            System.out.println("invalid description");
            return false;
        }

        Date date = conference.getDate();
        Date now = new Date();
        if (date.compareTo(now) < 0) {
            return false;
        }

        String address = conference.getAddress();
        if (!isMatches(address, AT_LEAST_FIVE_LETTERS_PATTERN)) {
            return false;
        }

        String name = conference.getName();
        return isMatches(name, AT_LEAST_FIVE_LETTERS_PATTERN);
    }
}
