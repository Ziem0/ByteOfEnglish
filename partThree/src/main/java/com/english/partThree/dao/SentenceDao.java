package com.english.partThree.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SentenceDao {

    private static SentenceDao dao = null;
    private Connection connection;
    private PreparedStatement preparedStatement = null;

    private SentenceDao() {
        this.connection = DatabaseConnection.getConn();
    }

    public static SentenceDao getDao() {
        if (dao == null) {
            synchronized (SentenceDao.class) {
                if (dao == null) {
                    dao = new SentenceDao();
                }
            }
        }
        return dao;
    }

    private void setPreparedStatement(String query) {
        try {
            preparedStatement = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closePrepareStatement() {

        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}
