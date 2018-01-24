package by.epam.tc.conference.services.validator;

import by.epam.tc.conference.entity.Conference;

public class ConferenceValidator implements Validator<Conference> {
    @Override
    public boolean validate(Conference object) {
        return false;
    }
}
