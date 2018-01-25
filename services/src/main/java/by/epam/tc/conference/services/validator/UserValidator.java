package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.User;

import java.util.regex.Pattern;

/**
 * Logic to validate {@link User}
 * Implements {@link Validator}
 */
public class UserValidator extends AbstractValidator<User> {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("(?=.{5,16}$)([a-zA-Z][^\\W_]+)");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "(?=.{6,16}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*");
    private static final Pattern NAME_PATTERN = Pattern.compile("(?=.{2,20}$)\\S+");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validate(User user) {
        if (user == null) {
            return false;
        }
        Long id = user.getId();
        if (id != null && id <= 0) {
            return false;
        }

        String firstName = user.getFirstName();
        if (!isMatches(firstName, NAME_PATTERN)) {
            return false;
        }

        String lastName = user.getLastName();
        if (!isMatches(lastName, NAME_PATTERN)) {
            return false;
        }

        String username = user.getUsername();
        if (!isMatches(username, USERNAME_PATTERN)) {
            return false;
        }

        String email = user.getEmail();
        if (!isMatches(email, EMAIL_PATTERN)) {
            return false;
        }

        String password = user.getPassword();
        return isMatches(password, PASSWORD_PATTERN);
    }

}
