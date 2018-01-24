package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Message;

public class MessageValidator implements Validator<Message> {
    @Override
    public boolean validate(Message object) {
        return false;
    }
}
