package by.epam.tc.conference.dao.mysql.pool;

public class ConnectionPoolInitializationException extends RuntimeException {

    public ConnectionPoolInitializationException(String message) {
        super(message);
    }

    public ConnectionPoolInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolInitializationException(Throwable cause) {
        super(cause);
    }
}
