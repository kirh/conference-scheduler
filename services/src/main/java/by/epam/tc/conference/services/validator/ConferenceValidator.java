package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Conference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Logic to validate {@link Conference}.
 * Implements {@link Validator}
 */
public class ConferenceValidator extends AbstractValidator<Conference> {

    private static final Logger logger = LogManager.getLogger(ConferenceValidator.class);
    private static final Pattern AT_LEAST_FIVE_LETTERS_PATTERN = Pattern.compile("(?:\\S\\s?){5,}");
    private static final Pattern AT_LEAST_TWENTY_LETTERS_PATTERN = Pattern.compile(".{20,}", Pattern.DOTALL);

    @Override
    public boolean validate(Conference conference) {
        logger.debug("Validating conference");
        if (conference == null) {
            logger.debug("Invalid:conference=null");
            return false;
        }

        Long id = conference.getId();
        if (id != null && id <= 0) {
            logger.debug("Invalid:id={}", id);
            return false;
        }

        Long administratorId = conference.getAdministratorId();
        if (!isValidId(administratorId)) {
            logger.debug("Invalid:administratorId={}", administratorId);
            return false;
        }

        String description = conference.getDescription();
        if (!isMatches(description, AT_LEAST_TWENTY_LETTERS_PATTERN)) {
            logger.debug("Invalid:description should be at least twenty letters", description);
            return false;
        }

        Date date = conference.getDate();
        Date now = new Date();
        if (date.compareTo(now) < 0) {
            logger.debug("Invalid:date={} Should be in future", date);
            return false;
        }

        String address = conference.getAddress();
        if (!isMatches(address, AT_LEAST_FIVE_LETTERS_PATTERN)) {
            logger.debug("Invalid:address={} Should contain at least five letters", address);
            return false;
        }

        String name = conference.getName();
        if (!isMatches(name, AT_LEAST_FIVE_LETTERS_PATTERN)) {
            logger.debug("Invalid:name={} Should contain at least five letters", name);
            return false;
        }
        return true;
    }
}
