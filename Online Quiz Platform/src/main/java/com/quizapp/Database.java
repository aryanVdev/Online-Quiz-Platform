package com.quizapp;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        try (InputStream in = Database.class.getResourceAsStream("/db.properties")) {
            Properties p = new Properties();
            if (in != null) p.load(in);
            String host = p.getProperty("db.host", "localhost");
            String port = p.getProperty("db.port", "3306");
            String db   = p.getProperty("db.name", "quizdb");
            USER        = p.getProperty("db.user", "root");
            PASS        = p.getProperty("db.password", "");

            URL = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
