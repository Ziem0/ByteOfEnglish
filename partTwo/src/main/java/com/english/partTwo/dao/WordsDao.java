package com.english.partTwo.dao;

import com.english.partTwo.exceptions.InvalidIDException;
import com.english.partTwo.models.Word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WordsDao {

    private static WordsDao dao = null;
    private static Connection conn;
    private PreparedStatement preparedStatement;

    private WordsDao() {
        conn = DataBaseConnection.getConn();
    }

    public static WordsDao getDao() {
        if (dao == null) {
            synchronized (WordsDao.class) {
                if (dao == null) {
                    dao = new WordsDao();
                }
            }
        }
        return dao;
    }

    public void loadCSV() {
        String csvFile = "partTwo/src/main/resources/data.csv";
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            preparedStatement = conn.prepareStatement("insert into words(eng,pl,date,hour,repeated) values(?,?,?,?,?);");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                preparedStatement.setString(1, values[0]);
                preparedStatement.setString(2, values[1]);
                String dateN = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                preparedStatement.setString(3, dateN);
                String hourN = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
                preparedStatement.setString(4, hourN);
                preparedStatement.setInt(5, 0);
                preparedStatement.executeUpdate();
            }

        } catch (FileNotFoundException e) {
            System.out.println("WordsDao.loadCSV error");
        } catch (IOException e) {
            System.out.println("WordsDao.loadCSV read error");
        } catch (SQLException e) {
            System.out.println("WordsDao.loadCSV preparedStat error");
        } finally {
            if (br == null) {
                try {
                    br.close();
                    preparedStatement.close();
                } catch (IOException e) {
                    System.out.println("WordsDao.loadCSV close error");
                } catch (SQLException e) {
                    System.out.println("WordsDao.loadCSV preparedStat close error");
                }
            }
        }
    }

    public int getLastID() {
        int id = Integer.parseInt(null);
        String query = "select count(id) from words;";
        try {
            preparedStatement = conn.prepareStatement(query);
            ResultSet result = preparedStatement.getResultSet();
            id = result.getInt(1);
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.getLastID error");
        }
        return id;
    }

    public Word getWordByID(int id) throws InvalidIDException {
        String query = "select words.*, status.name from words join status on status.id=words.statusID where id=?;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.getResultSet();
            return this.createWord(result);
        } catch (SQLException e) {
            throw new InvalidIDException("WordsDao.getWordByID error");
        }
    }

    private Word createWord(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String eng = result.getString(2);
        String pl = result.getString(3);
        int statusID = result.getInt(4);
        String date = result.getString(5);
        LocalDate ld = LocalDate.parse(date);
        String hour = result.getString(6);
        LocalTime lh = LocalTime.parse(hour);
        int repeated = result.getInt(7);
        String status = result.getString(8);
        return new Word(id, eng, pl, status, statusID, ld, lh, repeated);
    }

    public void updateWord(Word word) {
        String eng = word.getEng();
        String pl = word.getPl();
        int statusID = word.getStatusID();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        int repeated = word.getRepeated() + 1;
        int id = word.getId();
        String command = "update words set eng=?, pl=?, statusID=?, date=?, hour=?, repeated=? where id=?;";

        try {
            preparedStatement = conn.prepareStatement(command);
            preparedStatement.setString(1, eng);
            preparedStatement.setString(2, pl);
            preparedStatement.setInt(3, statusID);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, hour);
            preparedStatement.setInt(6, repeated);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("WordsDao.updateWord error");
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("WordsDao.updateWord close error");
            }
        }
    }

    public void deleteWord(Word word) {
        int id = word.getId();
        String command = "delete from words where id=?;";
        try {
            preparedStatement = conn.prepareStatement(command);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("WordsDao.deleteWord error");
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("WordsDao.deleteWord close error");
            }
        }
    }

    public void addWord(Word word) {
        String eng = word.getEng();
        String pl = word.getPl();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        String command = "insert into words(eng,pl,date,hour) values(?,?,?,?);";
        try {
            preparedStatement = conn.prepareStatement(command);
            preparedStatement.setString(1, eng);
            preparedStatement.setString(2, pl);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, hour);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("WordsDao.addWord error");
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("WordsDao.addWord close error");
            }
        }
    }
}
