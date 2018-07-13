package com.english.partTwo.dao;

import com.english.partTwo.models.Word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WordsDao {

    private static WordsDao dao = null;
    private final Connection conn;
    private PreparedStatement preparedStatement = null;

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
        int id = 0;
        String query = "select count(id) from words;";
        try {
            Statement stat = conn.createStatement();
            stat.execute(query);
            ResultSet result = stat.getResultSet();
            id = result.getInt(1);
            result.close();
            stat.close();
            return id;
        } catch (SQLException e) {
            System.out.println("WordsDao.getLastID error");
            System.out.println("lastid"+id);
            return id;
        }
    }

    public HashMap<Integer, List<Integer>> getAllUnknownID() {
        return getSelectedStatusIDs("unknown");
    }

    public HashMap<Integer, List<Integer>> getAllMiddleknownID() {
        return getSelectedStatusIDs("middleKnown");
    }

    public HashMap<Integer, List<Integer>> getAllKnownID() {
        return getSelectedStatusIDs("known");
    }

    private HashMap<Integer, List<Integer>> getSelectedStatusIDs(String name) {
        HashMap<Integer, List<Integer>> allID = new HashMap<>();
        String query = "select words.id, repeated from words join status on status.id = words.statusID where status.name =? order by repeated ASC;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                int repeated = result.getInt(2);
                if (!allID.containsKey(repeated)) {
                    allID.put(repeated, new LinkedList<>());
                }
                allID.get(repeated).add(id);
            }
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.getSelectedStatusIDs error");
        }
        return allID;
    }

    public Word getWordByID(int id) {
        Word w = null;
        String query = "select words.*, status.name from words join status on status.id=words.statusID where words.id=?;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            w = this.createWord(result);
            return w;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    private Word createWord(ResultSet result) throws SQLException {
        int id = result.getInt(1);
        String eng = result.getString(2);
        String pl = result.getString(3);
        int statusID = result.getInt(4);
        String date = result.getString(5);
        LocalDate ld = LocalDate.parse(date);
        String hour = result.getString(6);
        hour = hour.replaceAll("-", ":");
        LocalTime lh = LocalTime.parse(hour);
        int repeated = result.getInt(7);
        String status = result.getString(8);
        result.close();
        preparedStatement.close();
        return new Word(id, eng, pl, status, statusID, ld, lh, repeated);
    }

    public void updateWord(Word word) {
        String eng = word.getEng();
        String pl = word.getPl();
        int statusID = word.getStatusID();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        int repeated = word.getRepeated();
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
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.updateWord error");
        }
    }

    public void deleteWord(int id) {
        String command = "delete from words where id=?;";
        try {
            preparedStatement = conn.prepareStatement(command);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.deleteWord error");
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
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.addWord error");
        }
    }

    public LocalDate getOldestDate() {
        LocalDate ld = null;
        try {
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("select min(date) from words;");
            String date = result.getString(1);
            ld = LocalDate.parse(date);
            result.close();
            stat.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.getOldestDate error");
        }
        return ld;
    }

    public List<Integer> getWordsFromLastWords(String date) {
        List<Integer> wordsIDs = new LinkedList<>();
        String query = "select words.id from words where words.date >= ? order by date ASC;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, date);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                wordsIDs.add(result.getInt(1));
            }
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("WordsDao.getWordsFromLastWords error");
        }
        return wordsIDs;
    }


    public static void main(String[] args) {
        WordsDao dao = new WordsDao();
        dao.getAllUnknownID();
//        Word w = new Word(2998, "zone", "strefa", "unknown", 1, LocalDate.now(), LocalTime.now(), 0);
//        dao.updateWord(w);
        DataBaseConnection.close();
    }
}
