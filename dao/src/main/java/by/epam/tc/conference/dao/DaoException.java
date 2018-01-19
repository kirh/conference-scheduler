package by.epam.tc.conference.dao;

import by.epam.tc.conference.commons.ConferenceException;

public class DaoException extends ConferenceException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
