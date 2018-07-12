package com.english.partTwo.dao;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection conn = null;
    private static final String DB_URL = "jdbc:sqlite:partTwo/src/main/resources/words.db";

    private DataBaseConnection() {
    }

    public static Connection getConn() {
        if (conn == null) {
            synchronized (DataBaseConnection.class) {
                if (conn == null) {
                    createConnection();
                }
            }
        }
        return conn;
    }

    private static void createConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("DataBaseConnection.createConnection error");
        }
    }

    private static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("DataBaseConnection.close error");
        }
    }

    private static void getMigration() {
        Flyway fw = new Flyway();
        fw.setDataSource(DB_URL, null, null);
        fw.clean();
        fw.migrate();
    }

    public static void main(String[] args) {
        conn = getConn();
        getMigration();
        WordsDao w = WordsDao.getDao();
        w.loadCSV();
        close();
    }
}