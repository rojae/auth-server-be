package tc.mariadb;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public class MariadbInitTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Container
    JdbcDatabaseContainer mariaDB = new MariaDBContainer("mariadb:10.5")
            .withUsername("sa")
            .withPassword("password")
            .withDatabaseName("testdb")
            .withInitScript("sql/schema.sql");

    @Test
    void connect() {
        logger.info("username: {}", mariaDB.getUsername());
        logger.info("password: {}", mariaDB.getPassword());
        logger.info("jdbc url: {}", mariaDB.getJdbcUrl());

        try (Connection conn = DriverManager.getConnection(mariaDB.getJdbcUrl(), mariaDB.getUsername(), mariaDB.getPassword())) {
            logger.info("got connection");
            Thread.sleep(30 * 1000);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}