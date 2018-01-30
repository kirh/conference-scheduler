package by.epam.tc.conference.dao.mysql.pool;

import java.util.ResourceBundle;

/**
 * Provides access to db connection parameters
 */
public final class DbResourceManager {

    private static final DbResourceManager instance = new DbResourceManager();
    private final ResourceBundle bundle = ResourceBundle.getBundle("dbcloud");

    private DbResourceManager() {

    }

    public static DbResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
