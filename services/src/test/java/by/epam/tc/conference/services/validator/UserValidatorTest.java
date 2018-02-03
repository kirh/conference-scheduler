package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidatorTest {

    private User user;

    private UserValidator validator = new UserValidator();

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setEmail("user@email.ru");
        user.setFirstName("vasya");
        user.setLastName("pupkin");
        user.setPassword("Password1");
        user.setAdmin(true);
    }

    @Test
    public void shouldBeValidWhenValidUserGiven() {
        boolean isValid = validator.validate(user);

        assertTrue(isValid);
    }

    @Test
    public void shouldBeInvalidWhenUsernameStartsWithDigit() {
        user.setUsername("1user1");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenUsernameLessThenFiveLetters() {
        user.setUsername("user");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenUsernameMoreThenSixteenLetters() {
        String seventeenA = String.format("%17s", " ").replace(" ","A");
        user.setUsername(seventeenA);

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenUsernameContainsUnderscore() {
        user.setUsername("user_1");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenPasswordNotContainUpperCaseLetter() {
        user.setPassword("password1");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenPasswordNotContainDigit() {
        user.setPassword("Password");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenPasswordLessSixSymbols() {
        user.setPassword("Pass1");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenFirstNameLessTwoLetters() {
        user.setFirstName("a");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenLastNameLessTwoLetters() {
        user.setLastName("a");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }

    @Test
    public void shouldBeInvalidWhenInvalidEmailGiven() {
        user.setEmail("email@email");

        boolean isValid = validator.validate(user);

        assertFalse(isValid);
    }
}