package com.english.partThree.dao;

import org.flywaydb.core.Flyway;
import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:partThree/src/main/resources/database.db";
    private static Connection conn = null;

    private DatabaseConnection() {
    }

    public static Connection getConn() {
        if (conn == null) {
            synchronized (DatabaseConnection.class) {
                if (conn == null) {
                    createConnection();
                }
            }
        }
        return conn;
    }

    private static void createConnection() {
        try {
            DriverManager.registerDriver(new JDBC());
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void migrate() {
        Flyway fw = new Flyway();
        fw.setDataSource(DB_URL, null, null);
        fw.clean();
        fw.migrate();
    }
}
