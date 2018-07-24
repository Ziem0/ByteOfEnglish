package com.english.partThree.dao;

import com.english.partThree.enums.CsvPath;
import com.english.partThree.enums.EditablePart;
import com.english.partThree.interfaces.ExAble;
import com.english.partThree.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

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

    ///////////////////////////////////////////////////////////////////////

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
            System.out.println("Skipped incorrect word. Program will continue..");
        }
    }
    ///////////////////////////////////////////////////////////////////////

    public void updateWord(AbstractModel model) {
        String query = "update " + model.getClass().getSimpleName().toLowerCase() + " set eng=?, pl=?, used=?, date=?, hour=?, favorite=? where id=?;";

        int id = model.getId();
        String eng = model.getEng();
        String pl = model.getPl();
        int used = model.getUsed();
        String date = model.getLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String hour = model.getLocalTime().format(DateTimeFormatter.ofPattern("hh-mm-ss"));
        int favorite = model.getFavorite();

        try {
            setPreparedStatement(query);
            preparedStatement.setString(1, eng);
            preparedStatement.setString(2, pl);
            preparedStatement.setInt(3, used);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, hour);
            preparedStatement.setInt(6, favorite);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            closePrepareStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <E extends ExAble> void updateExplainAndExample(E model, int id) {
        String partName = model.getClass().getSimpleName();
        String query = "update " + partName + " set explain=?, example=? where id=?;";

        String example = model.getExample();
        String explain = model.getExplain();

        try {
            setPreparedStatement(query);
            preparedStatement.setString(1, explain);
            preparedStatement.setString(2, example);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            closePrepareStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateGrama(AbstractGramaModel grama) {
        String query = "update " + grama.getClass().getSimpleName() + " set used=?, date=?, hour=? where id=?;";

        int used = grama.getUsed();
        String date = grama.getLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String hour = grama.getLocalTime().format(DateTimeFormatter.ofPattern("hh-mm-ss"));
        int id = grama.getId();

        try {
            setPreparedStatement(query);
            preparedStatement.setInt(1, used);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, hour);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            closePrepareStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////

    public AbstractModel getWordByID(String modelType, int id) {
        String query = "select * from " + modelType + " where id = ?;";

        try {
            setPreparedStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet r = preparedStatement.executeQuery();
            if (r.next()) {
                return createWord(r, modelType);
            }
            r.close();
            closePrepareStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AbstractGramaModel getGramaByID(String modelType, int id) {
        String query = "select * from " + modelType + " where id = ?;";

        try {
            setPreparedStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet r = preparedStatement.executeQuery();
            if (r.next()) {
                return createGrama(r, modelType);
            }
            r.close();
            closePrepareStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AbstractModel createWord(ResultSet r, String modelType) {
        AbstractModel word = null;
        try {
            int idU = r.getInt(1);
            String engU = r.getString(2);
            String plU = r.getString(3);
            int usedU = r.getInt(4);
            String localDateU = r.getString(5);
            LocalDate ld = LocalDate.parse(localDateU);
            String[] localTimeU = r.getString(6).split("-");
            LocalTime lt = LocalTime.of(Integer.parseInt(localTimeU[0]),Integer.parseInt(localTimeU[1]),Integer.parseInt(localTimeU[2]));
            int favoriteU = r.getInt(7);

            switch (modelType) {
                case "noun":
                    word = new Noun(idU, engU, plU, usedU, ld, lt, favoriteU);
                    break;
                case "adjective":
                    word = new Adjective(idU, engU, plU, usedU, ld, lt, favoriteU);
                    break;
                case "idiom":
                    String explainI = r.getString(8);
                    String exampleI = r.getString(9);
                    word = new Idiom(idU, engU, plU, usedU, ld, lt, favoriteU, explainI, exampleI);
                    break;
                case "verb":
                    String simpleU = r.getString(8);
                    String perfectU = r.getString(9);
                    word = new Verb(idU, engU, plU, usedU, ld, lt, favoriteU, simpleU, perfectU);
                    break;
                case "phrasal":
                    String explain = r.getString(8);
                    String example = r.getString(9);
                    word = new Phrasal(idU, engU, plU, usedU, ld, lt, favoriteU, explain, example);
                    break;
                default:
                    System.out.println("Incorrect wordType");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }

    private AbstractGramaModel createGrama(ResultSet r, String modelType) {
        AbstractGramaModel grama = null;
        try {
            int idU = r.getInt(1);
            String nameU = r.getString(2);
            int usedU = r.getInt(3);
            String localDateU = r.getString(4);
            LocalDate ld = LocalDate.parse(localDateU);
            String[] localTimeU = r.getString(5).split("-");
            LocalTime lt = LocalTime.of(Integer.parseInt(localTimeU[0]),Integer.parseInt(localTimeU[1]),Integer.parseInt(localTimeU[2]));

            switch (modelType) {
                case "grama":
                    grama = new Grama(idU, nameU, usedU, ld, lt);
                    break;
                case "tense":
                    grama = new Tense(idU, nameU, usedU, ld, lt);
                    break;
                case "type":
                    grama = new Type(idU, nameU, usedU, ld, lt);
                    break;
                default:
                    System.out.println("Incorrect gramaType");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grama;
    }

    public Person createPersonByID(int id) {
        String query = "select * from person where id=?;";
        setPreparedStatement(query);
        try {
            preparedStatement.setInt(1, id);
            ResultSet r = preparedStatement.executeQuery();
            if (r.next()) {
                int id_ = r.getInt(1);
                String eng = r.getString(2);
                String pl = r.getString(3);
                int used = r.getInt(4);
                return new Person(id, eng, pl, used);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    }
    ////////////////////////////////////////////////////////////////////////

    public int getMaxId(String option) {
        int amount = 0;
        String query = "";
        switch (option) {
            case "type":
                query = "select count(id) from type";
                break;
            case "grama":
                query = "select count(id) from grama";
                break;
            case "tense":
                query = "select count(id) from tense";
                break;
            case "person":
                query = "select count(id) from person";
                break;
            case "verb":
                query = "select count(id) from verb";
                break;
            case "adjective":
                query = "select count(id) from adjective";
                break;
            case "noun":
                query = "select count(id) from noun";
                break;
            case "phrasal":
                query = "select count(id) from phrasal";
                break;
            case "idiom":
                query = "select count(id) from idiom";
                break;
        }
        setPreparedStatement(query);
        try {
            ResultSet r = preparedStatement.executeQuery();
            if (r.next()) {
                amount = r.getInt(1);
            }
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closePrepareStatement();
        return amount;
    }

    ////////////////////////////////////////////////////////////////////////

    public List<Integer> getMostLeastRepeated(String LeastOrMost,int amount, String model) {
        List<Integer> ids = new LinkedList<>();

        String query = "";
        if (LeastOrMost.equals("most")) {
            query = "select id from " + model + " order by used DESC limit ?;";
        } else {
            query = "select id from " + model + " order by used ASC limit ?;";
        }

        createListWithIds(amount, ids, query);
        return ids;
    }

    public List<Integer> getByDate(String newOrOld,int amount, String model) {
        List<Integer> ids = new LinkedList<>();

        String query = "";
        if (newOrOld.equals("new")) {
            query = "select id from " + model + " order by date, hour DESC limit ?;";
        } else {
            query = "select id from " + model + " order by date, hour ASC limit ?;";
        }

        createListWithIds(amount, ids, query);
        return ids;
    }

    private void createListWithIds(int amount, List<Integer> ids, String query) {
        try {
            setPreparedStatement(query);
            preparedStatement.setInt(1, amount);
            ResultSet r = preparedStatement.executeQuery();
            while (r.next()) {
                ids.add(r.getInt(1));
            }
            r.close();
            closePrepareStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getByFavorite(String model) {
        List<Integer> ids = new LinkedList<>();

        String query = "select id from " + model + " where favorite is 1 limit ?;";

        createListWithIds(3000, ids, query);
        return ids;
    }

    public boolean isFavoritePossible() {
        for (EditablePart model : EditablePart.values()) {
            String query = "select count(id) from " + model + " where favorite is 1;";
            try {
                setPreparedStatement(query);
                ResultSet r = preparedStatement.executeQuery();
                if (r.getInt(1) == 0) {
                    r.close();
                    closePrepareStatement();
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
