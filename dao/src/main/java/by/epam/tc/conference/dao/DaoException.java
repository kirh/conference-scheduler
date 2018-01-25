package by.epam.tc.conference.dao;

import by.epam.tc.conference.commons.ConferenceException;

/**
 * General exception for dao layer.
 */
public class DaoException extends ConferenceException {

    private static final long serialVersionUID = 42L;

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
