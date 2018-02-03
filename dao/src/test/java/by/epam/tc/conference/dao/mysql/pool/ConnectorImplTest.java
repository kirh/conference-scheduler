package by.epam.tc.conference.dao.mysql.pool;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectorImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @InjectMocks
    private ConnectorImpl connector;

    @Before
    public void setUp() throws Exception {
        when(connectionPool.takeConnection()).thenReturn(connection);
    }

    @Test
    public void shouldPrepareStatementWhenSqlQueryAndKeyFlagGiven() throws SQLException {

        connector.prepareStatement("query", Statement.RETURN_GENERATED_KEYS);

        verify(connection).prepareStatement("query", Statement.RETURN_GENERATED_KEYS);
    }

    @Test
    public void shouldDisableAutocommitWhenStartTransaction() throws SQLException {
        connector.startTransaction();

        verify(connection).setAutoCommit(false);
    }

    @Test
    public void shouldEnableAutoCommitOnCommit() throws SQLException {
        connector.startTransaction();
        connector.commitTransaction();

        verify(connection).setAutoCommit(true);
    }

    @Test
    public void shouldNotReleaseConnectionWhenTransactionStartedOnCloseConnection() throws SQLException {
        connector.startTransaction();
        connector.startTransaction();

        connector.closeConnection();
        verify(connectionPool, never()).putConnection(connection);
    }

    @Test
    public void shouldReleaseConnectionWhenNoOpenTransactionOnCloseConnection() {
        connector.closeConnection();

        verify(connectionPool).putConnection(connection);
    }

    @Test
    public void shouldReleaseConnectionWhenTransactionCommited() throws SQLException {
        connector.startTransaction();
        connector.commitTransaction();
        verify(connectionPool).putConnection(connection);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldBeExceptionWhenAttemptToCommitNotExistingTransaction() throws SQLException {
        connector.commitTransaction();
    }
}