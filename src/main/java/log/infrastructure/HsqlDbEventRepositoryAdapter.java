package log.infrastructure;

import log.domain.event.Event;
import log.domain.event.EventRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static log.domain.Constants.MAX_STRING_LENGTH;

public class HsqlDbEventRepositoryAdapter implements EventRepository {

    private static final String RESTART_DB_DATA_STATEMENT = "DROP SCHEMA PUBLIC CASCADE";
    private static final String CREATE_EVENTS_TABLE_STATEMENT_TEMPLATE = "create table events (id VARCHAR(%s), duration DECIMAL, host VARCHAR(%s), alert BOOLEAN)";
    private static final String SAVE_EVENT_STATEMENT_TEMPLATE = "insert into events VALUES ('%s', %d, '%s', %b)";

    private static final String HSQL_DRIVER_NAME = "org.hsqldb.jdbc.JDBCDriver";
    private static final String DB_URL = "jdbc:hsqldb:mydatabase";
    private static final String DB_USER = "SA";
    private static final String DB_PASSWORD = "";

    public HsqlDbEventRepositoryAdapter() {
        loadHsqlDbDriver();
        clearDatabase();
        setupDatabase();
    }

    @Override
    public void save(Event event) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            connection.createStatement()
                    .execute(String.format(SAVE_EVENT_STATEMENT_TEMPLATE, event.getId(), event.getDuration(), event.getHost(), event.isAlert()));

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private void loadHsqlDbDriver() {
        try {
            Class.forName(HSQL_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
    }

    private void setupDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            connection.createStatement()
                    .execute(String.format(CREATE_EVENTS_TABLE_STATEMENT_TEMPLATE, MAX_STRING_LENGTH, MAX_STRING_LENGTH));

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private void clearDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            connection.createStatement()
                    .execute(RESTART_DB_DATA_STATEMENT);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
