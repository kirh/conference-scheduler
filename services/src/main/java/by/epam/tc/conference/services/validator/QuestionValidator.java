package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Question;

public class QuestionValidator implements Validator<Question> {
    @Override
    public boolean validate(Question object) {
        return false;
    }
}
