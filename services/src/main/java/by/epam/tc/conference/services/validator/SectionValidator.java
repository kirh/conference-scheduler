package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Section;

import java.util.regex.Pattern;

/**
 * Logic to validate {@link Section}
 * Implements {@link Validator}
 */
public class SectionValidator extends AbstractValidator<Section> {

    private static final Pattern AT_LEAST_FIVE_LETTERS_PATTERN = Pattern.compile("(\\S\\s*){5,}");

    @Override
    public boolean validate(Section section) {
        if (section == null) {
            return false;
        }

        Long id = section.getId();
        if (id != null && id <=0) {
            return false;
        }

        Long conferenceId = section.getConferenceId();
        if (!isValidId(conferenceId)) {
            return false;
        }

        String topic = section.getTopic();
        return isMatches(topic, AT_LEAST_FIVE_LETTERS_PATTERN);
    }
}
