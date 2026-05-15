package com.cybernova.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    static {
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("db.properties not found in resources");
            }

            Properties props = new Properties();
            props.load(input);

            DB_URL = props.getProperty("db.url");
            DB_USER = props.getProperty("db.user");
            DB_PASSWORD = props.getProperty("db.password");

            if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
                throw new RuntimeException("Missing DB config in db.properties");
            }

            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            throw new RuntimeException("Database init failed: " + e.getMessage(), e);
        }
    }

    public static Connection openConnection() throws Exception {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB connection failed: " + e.getMessage(), e);
        }
    }
}