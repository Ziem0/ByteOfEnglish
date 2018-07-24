package com.english.partTwo.dao;

import com.english.partTwo.enums.Colors;
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
            System.out.println(Colors.RED.getFg("WordsDao.loadCSV error"));
        } catch (IOException e) {
            System.out.println(Colors.RED.getFg("WordsDao.loadCSV read error"));
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("Database from CSV has been already loaded"));
        } finally {
            if (br == null) {
                try {
                    br.close();
                    preparedStatement.close();
                } catch (IOException e) {
                    System.out.println(Colors.RED.getFg("WordsDao.loadCSV close error"));
                } catch (SQLException e) {
                    System.out.println(Colors.RED.getFg("WordsDao.loadCSV preparedStat close error"));
                }
            }
        }
    }

    public int getLastID() {
        int id = 0;
        String query = "select id from words order by id desc limit 1;";
        try {
            Statement stat = conn.createStatement();
            stat.execute(query);
            ResultSet result = stat.getResultSet();
            id = result.getInt(1);
            result.close();
            stat.close();
            return id;
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getLastID error"));
            System.out.println("lastid"+id);
            return id;
        }
    }

//////////////
    public HashMap<Integer, List<Integer>> getAllUnknownID() {
        return getSelectedStatusIDs("unknown");
    }

    public HashMap<Integer, List<Integer>> getAllMiddleKnownID() {
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
            System.out.println(Colors.RED.getFg("WordsDao.getSelectedStatusIDs error"));
        }
        return allID;
    }
//////////////

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
            System.out.println(Colors.RED.getFg("WordsDao.getWordByID error"));
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
    //////////////

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
            System.out.println(Colors.RED.getFg("WordsDao.updateWord error"));
        }
    }

    public boolean deleteWord(int id) {
        String command = "delete from words where id=?;";
        try {
            preparedStatement = conn.prepareStatement(command);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.deleteWord error"));
            return false;
        }
        return true;
    }

    public boolean addWord(Word word) {
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
            System.out.println(Colors.RED.getFg("WordsDao.addWord error"));
            return false;
        }
        return true;
    }
    //////////////

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
            System.out.println(Colors.RED.getFg("WordsDao.getOldestDate error"));
        }
        return ld;
    }

    public int getSelectedIdStatusAmount(String status) {
        int amount = 0;
        String query = "select count(words.id) from words join status on words.statusID=status.id where status.name = ?;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, status);
            ResultSet result = preparedStatement.executeQuery();
            amount = result.getInt(1);
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getSelectedIdStatusAmount error"));
        }
        return amount;
    }

    public List<Integer> getSelectedIdStatusList(String status) {
        List<Integer> listIDs = new LinkedList<>();
        String query = "select words.id from words join status on words.statusID=status.id where status.name = ?;";
        try {
            makePreparedToCreateList(status, listIDs, query);
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getSelectedIdStatusAmount error"));
        }
        return listIDs;
    }

    public List<Integer> getWordsFromLastDays(String date) {
        List<Integer> wordsIDs = new LinkedList<>();
        String query = "select words.id from words where words.date >= ? order by date ASC;";
        try {
            makePreparedToCreateList(date, wordsIDs, query);
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getWordsFromLastWords error"));
        }
        return wordsIDs;
    }

    public List<Integer> getLastWords(int userAmount) {
        List<Integer> wordsIDs = new LinkedList<>();
        String query = "select id from words order by date desc, hour desc limit ?;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,userAmount);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                wordsIDs.add(result.getInt(1));
            }
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getLastWords error"));
        }
        return wordsIDs;
    }

    private void makePreparedToCreateList(String status, List<Integer> listIDs, String query) throws SQLException {
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, status);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            listIDs.add(result.getInt(1));
        }
        result.close();
        preparedStatement.close();
    }

    ////////////////////////////////////////////////////////////////////

    public int getProportion(String status) {
        int amount = 0;
        String query = "select count(words.id) from words join status on status.id=words.statusID where status.name=?;";
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, status);
            ResultSet result = preparedStatement.executeQuery();
            amount = result.getInt(1);
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getProportion error"));
        }
        return amount;
    }

    public List<Word> get10MostRepeated() {
        List<Word> listRepeated = new LinkedList<>();
        String query = "select words.*, status.name from words join status on words.statusID=status.id order by words.repeated DESC limit 10;";
        computeListLeastOrMostRepeated(listRepeated, query);
        return listRepeated;
    }

    private void computeListLeastOrMostRepeated(List<Word> listRepeated, String query) {
        try {
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery(query);
            while (result.next()) {
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
                listRepeated.add(new Word(id, eng, pl, status, statusID, ld, lh, repeated));
            }
            result.close();
            stat.close();
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.get30MostRepeated error"));
        }
    }

    public List<Word> get10LeastRepeated() {
        List<Word> listRepeated = new LinkedList<>();
        String query = "select words.*, status.name from words join status on words.statusID=status.id order by words.repeated ASC limit 10;";
        computeListLeastOrMostRepeated(listRepeated, query);
        return listRepeated;
    }


    public boolean getEngWordsStartingWith(String letters) {
        String query = "select words.id, words.eng, words.pl, status.name, words.date, words.hour, words.repeated from words " +
                "join status on status.id=words.statusID where words.eng like ? || '%';";
        return wordsStartingWithExe(letters, query);
    }

    public boolean getPlWordsStartingWith(String letters) {
        String query = "select words.id, words.pl, words.eng, status.name, words.date, words.hour, words.repeated from words " +
                "join status on status.id=words.statusID where words.pl like ? || '%';";
        return wordsStartingWithExe(letters, query);
    }

    private boolean wordsStartingWithExe(String letters, String query) {
        boolean found = false;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, letters);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                found = true;
                System.out.printf("id:%-15d rawLine:%-15s translated:%-15s status:%-15s date:%-10s hour:%-10s repeated:%d\n"
                        , result.getInt(1), result.getString(2), result.getString(3), result.getString(4)
                        , result.getString(5), result.getString(6), result.getInt(7));
            }
            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(Colors.RED.getFg("WordsDao.getWordsStartingWith error"));
        }
        return found;
    }
////////////////////////////////////////////////////////////////////
}
