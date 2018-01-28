package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Proposal;

import java.util.regex.Pattern;

/**
 * Logic to validate {@link Proposal}
 * Implements {@link Validator}
 */
public class ProposalValidator extends AbstractValidator<Proposal> {

    private static final Pattern AT_LEAST_FIVE_LETTERS_PATTERN = Pattern.compile("(?:\\S\\s*){5,}");
    private static final Pattern AT_LEST_TWENTY_LETTERS_PATTERN = Pattern.compile(".{20,}");

    @Override
    public boolean validate(Proposal proposal) {
        if (proposal == null) {
            return false;
        }

        Long id = proposal.getId();
        if (id != null && id <= 0) {
            return false;
        }

        Long participantId = proposal.getParticipantId();
        Long sectionId = proposal.getSectionId();
        if (!isValidId(participantId) || !isValidId(sectionId)) {
            return false;
        }

        String title = proposal.getTitle();
        if (!isMatches(title,AT_LEAST_FIVE_LETTERS_PATTERN)) {
            return false;
        }

        String description = proposal.getDescription();
        return isMatches(description, AT_LEST_TWENTY_LETTERS_PATTERN);
    }
}
