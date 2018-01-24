package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.User;

import java.util.function.Predicate;

public class UserValidator implements Validator<User> {

    public boolean validate(User user) {
        return true;
    }

}
