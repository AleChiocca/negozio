package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
//    private static final String URL = "jdbc:mysql://localhost:3306/negozio";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";

    private final Properties prop ;

    public DatabaseManager() {
        prop = new Properties();
        loadProperties();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }

    private void loadProperties() {

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("app.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "File app.properties non trovato!"
                );
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Errore nel caricamento delle configurazioni", e
            );
        }
    }

    private String getUrl() {
        return prop.getProperty("db.baseUrl");
    }

    private String getUsername() {
        return prop.getProperty("db.user");
    }

    private String getPassword() {
        return prop.getProperty("db.pass");
    }





}
