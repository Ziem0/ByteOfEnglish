package com.english.partThree.dao;

import com.english.partThree.enums.CsvPath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SentenceDao {

    private static SentenceDao dao = null;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private CsvPath csvPath;

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

    ///////////////////////////////////////////////////////////////////////
    public void loadAllCsv() {
        csvReader(CsvPath.VERBS_PATH);
        csvReader(CsvPath.NOUNS_PATH);
        csvReader(CsvPath.ADJECTIVES_PATH);
        csvReader(CsvPath.IDIOMS_PATH);
        csvReader(CsvPath.PHRASAL_VERBS_PATH);
    }

    private void csvReader(CsvPath csvPath) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvPath.getPath()));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                insertCsvData(values, csvPath.getSqlStatement());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closePrepareStatement();
    }

    private void insertCsvData(String[] values, String sqlStatement) {
        int valuesAmount = values.length;
        setPreparedStatement(sqlStatement);

        for (int i = 1; i <= valuesAmount; i++) {
            try {
                preparedStatement.setString(i, values[i - 1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////

    public void updateWord() {

    }

    public void updateGrama() {

    }

    //x sentences with all for each
    //x sentences with only chosen level for each
    //x sentences with chosen level for each (level is limit)
    //x sentences with most repeated for each
    //x sentences with last repeated for each
    //add to favorite --> choose what

    //statistic: sum repeated for each

    //edit --> choose what

//    sqlite> select count(id) from verb;       1000 : 20 = 50
//    sqlite> select count(id) from noun;       1525 : 30 = 50,8
//    sqlite> select count(id) from adjective;  143  : 4  = 35,75
//    sqlite> select count(id) from idiom;      300  : 10 = 30
//    sqlite> select count(id) from phrasal;    95   : 5  = 19


    public static void main(String[] args) {
        SentenceDao dao = getDao();
        DatabaseConnection.migrate();
        dao.loadAllCsv();
    }
}
