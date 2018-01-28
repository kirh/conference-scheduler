package by.epam.tc.conference.dao.mysql.pool;

/**
 * Wrapper for checked exceptions in connection pool
 */
public class ConnectionPoolException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
