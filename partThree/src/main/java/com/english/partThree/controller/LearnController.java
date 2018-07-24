package com.english.partThree.controller;

import com.english.partThree.dao.DatabaseConnection;
import com.english.partThree.dao.SentenceDao;
import com.english.partThree.enums.EditablePart;
import com.english.partThree.enums.LearnOptions;
import com.english.partThree.enums.PartOptions;
import com.english.partThree.enums.SubMenuOptions;
import com.english.partThree.model.*;
import com.english.partTwo.View.MainView;
import com.english.partTwo.enums.Colors;

import java.util.*;
import java.util.stream.Collectors;

public class LearnController {

    private SentenceDao dao;

    public LearnController() {
        this.dao = SentenceDao.getDao();
        DatabaseConnection.migrate();
        sleep();
//        dao.loadAllCsv();
        startController();
    }

    private void startController() {
        boolean start = true;

        while (start) {
            cleaner();
            LearnOptions.print();
            MainView.showMessage("Choose option: ");
            int opt = MainView.getUserNum(LearnOptions.values().length);

            switch (opt) {
                case 1:
                    startAll();
                    break;
                case 2:
                    startLevelLimited();          //---> unique
                    break;
                case 3:
                    startSelectedLevel();
                    break;
                case 4:
                    startMostLeastRepeated("most");
                    break;
                case 5:
                    startMostLeastRepeated("least");
                    break;
                case 6:
                    startByDatetime("new");
                    break;
                case 7:
                    startByDatetime("old");
                    break;
                case 8:
                    if (dao.isFavoritePossible()) {
                        startFavorite();
                    } else {
                        MainView.showMessage("This mode is locked. You don't have enough 'favorite' part(s)!\n");
                        sleep();
                    }
                    break;
                case 9:
                    start = false;
                    DatabaseConnection.closeConnection();
                    break;
            }
        }
    }

    /////////////////////////////////
    private List<Integer> getRandomInt(int maxID) {
        Random random = new Random();
        return random.ints(1, maxID).distinct().limit(1).boxed().collect(Collectors.toList());
    }

    private List<Integer> getRandomIntForLevel(int minID,int maxID) {
        Random random = new Random();
        return random.ints(minID, maxID).distinct().limit(1).boxed().collect(Collectors.toList());
    }

    private void sleep() {
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cleaner() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void display(Sentence sentence) {
        Scanner sc = new Scanner(System.in);

        cleaner();
        System.out.println(sentence.printPL());
        MainView.showMessage("\nEnter to continue..\n");
        sc.nextLine();
        System.out.println(sentence.printENG());
        MainView.showMessage("\nEnter to continue..\n");
        sc.nextLine();

        sentence.upgradeAll(dao);
        handleSubMenu(sentence);
    }

    private void handleSubMenu(Sentence sentence) {
        SubMenuOptions.print();
        MainView.showMessage("Choose option:");

        String user = MainView.getUserString().toLowerCase();

        switch (user) {
            case "":   //continue
                break;
            case "e":   //edit:
                editHandle(sentence);
                cleaner();
                System.out.println(sentence.printENG());
                handleSubMenu(sentence);
                break;
            case "a":   //favorite:
                favoriteHandle(sentence);
                cleaner();
                System.out.println(sentence.printENG());
                handleSubMenu(sentence);
                break;
            default:
                System.out.println(Colors.RED.getFg("\nIncorrect option! Try again"));
                sleep();
                cleaner();
                System.out.println(sentence.printENG());
                handleSubMenu(sentence);
                break;
        }
    }

    private void editHandle(Sentence sentence) {
        cleaner();
        EditablePart.print();
        MainView.showMessage("What part of sentence you want to modify: ");
        int user = MainView.getUserNum(EditablePart.values().length);

        switch (user) {
            case 1: //verb:
                sentence.getVerb().modify();
                dao.updateWord(sentence.getVerb());
                System.out.println("Word has been modified");
                sleep();
                break;
            case 2: //adjective
                sentence.getAdjective().modify();
                dao.updateWord(sentence.getAdjective());
                System.out.println("Word has been modified");
                sleep();
                break;
            case 3: //noun
                sentence.getNoun().modify();
                dao.updateWord(sentence.getNoun());
                System.out.println("Word has been modified");
                sleep();
                break;
            case 4: //phrasal
                sentence.getPhrasal().modify();
                dao.updateWord(sentence.getPhrasal());
                dao.updateExplainAndExample(sentence.getPhrasal(),sentence.getPhrasal().getId());
                System.out.println("Word has been modified");
                sleep();
                break;
            case 5: //idiom
                sentence.getIdiom().modify();
                dao.updateWord(sentence.getIdiom());
                 dao.updateExplainAndExample(sentence.getIdiom(),sentence.getIdiom().getId());
                System.out.println("Word has been modified");
                sleep();
                break;
            case 6: //back
                break;
        }
    }

    private void favoriteHandle(Sentence sentence) {
        cleaner();
        EditablePart.print();
        MainView.showMessage("What part of sentence you want to add to favorite: ");
        int user = MainView.getUserNum(EditablePart.values().length);

        switch (user) {
            case 1: //verb:
                sentence.getVerb().setFavorite();
                dao.updateWord(sentence.getVerb());
                System.out.println("Added to 'favorite'");
                sleep();
                break;
            case 2: //adjective
                sentence.getAdjective().setFavorite();
                dao.updateWord(sentence.getAdjective());
                System.out.println("Added to 'favorite'");
                sleep();
                break;
            case 3: //noun
                sentence.getNoun().setFavorite();
                dao.updateWord(sentence.getNoun());
                System.out.println("Added to 'favorite'");
                sleep();
                break;
            case 4: //phrasal
                sentence.getPhrasal().setFavorite();
                dao.updateWord(sentence.getPhrasal());
                System.out.println("Added to 'favorite'");
                sleep();
                break;
            case 5: //idiom
                sentence.getIdiom().setFavorite();
                dao.updateWord(sentence.getIdiom());
                System.out.println("Added to 'favorite'");
                sleep();
                break;
            case 6: //back
                break;
        }
    }

    private List<Integer> chooseLevelForEachPart() {
        List<Integer> levels = new LinkedList<>();
        for (EditablePart e : EditablePart.values()) {
            MainView.showMessage("choose level for " + e.name() + ". Max level is " + e.getLevels());
            int level = MainView.getUserNum(e.getLevels());
            levels.add(level);
        }
        return levels;
    }
    /////////////////////////////////
    private void startAll() {
        cleaner();
        MainView.showMessage("Choose how many sentence(s) you want to:");
        int user = MainView.getUserNum(3000);

        while (user-- > 0) {
            Grama grama = (Grama) dao.getGramaByID("grama", getRandomInt(PartOptions.GRAMA.getMaxId()).get(0));
            Tense tense = (Tense) dao.getGramaByID("tense", getRandomInt(PartOptions.TENSE.getMaxId()).get(0));
            Type type = (Type) dao.getGramaByID("type", getRandomInt(PartOptions.TYPE.getMaxId()).get(0));
            Person person = dao.createPersonByID(getRandomInt(PartOptions.PERSON.getMaxId()).get(0));
            Verb verb = (Verb) dao.getWordByID("verb", getRandomInt(PartOptions.VERB.getMaxId()).get(0));
            Adjective adjective = (Adjective) dao.getWordByID("adjective", getRandomInt(PartOptions.ADJECTIVE.getMaxId()).get(0));
            Noun noun = (Noun) dao.getWordByID("noun", getRandomInt(PartOptions.NOUN.getMaxId()).get(0));
            Phrasal phrasal = (Phrasal) dao.getWordByID("phrasal", getRandomInt(PartOptions.PHRASAL_VERB.getMaxId()).get(0));
            Idiom idiom = (Idiom) dao.getWordByID("idiom", getRandomInt(PartOptions.IDIOM.getMaxId()).get(0));

            Sentence sentence = new Sentence(grama, tense, type, person, verb, adjective, noun, phrasal, idiom);
            display(sentence);
        }
    }

    /////////////////////////////////
    private void startLevelLimited() {
        cleaner();
        MainView.showMessage("Choose how many sentences you want to:");
        int user = MainView.getUserNum(3000);

        List<Integer> levels = chooseLevelForEachPart();
        int verbLevel = levels.get(0);
        int adjectiveLevel = levels.get(1);
        int nounLevel = levels.get(2);
        int phrasalLevel = levels.get(3);
        int idiomLevel = levels.get(4);

        while (user-- > 0) {
            Grama grama = (Grama) dao.getGramaByID("grama", getRandomInt(PartOptions.GRAMA.getMaxId()).get(0));
            Tense tense = (Tense) dao.getGramaByID("tense", getRandomInt(PartOptions.TENSE.getMaxId()).get(0));
            Type type = (Type) dao.getGramaByID("type", getRandomInt(PartOptions.TYPE.getMaxId()).get(0));
            Person person = dao.createPersonByID(getRandomInt(PartOptions.PERSON.getMaxId()).get(0));
            Verb verb = (Verb) dao.getWordByID("verb", getRandomInt(PartOptions.VERB.calculateMinMaxLevel(verbLevel).get(1)).get(0));
            Adjective adjective = (Adjective) dao.getWordByID("adjective", getRandomInt(PartOptions.ADJECTIVE.calculateMinMaxLevel(adjectiveLevel).get(1)).get(0));
            Noun noun = (Noun) dao.getWordByID("noun", getRandomInt(PartOptions.NOUN.calculateMinMaxLevel(nounLevel).get(1)).get(0));
            Phrasal phrasal = (Phrasal) dao.getWordByID("phrasal", getRandomInt(PartOptions.PHRASAL_VERB.calculateMinMaxLevel(phrasalLevel).get(1)).get(0));
            Idiom idiom = (Idiom) dao.getWordByID("idiom", getRandomInt(PartOptions.IDIOM.calculateMinMaxLevel(idiomLevel).get(1)).get(0));

            Sentence sentence = new Sentence(grama, tense, type, person, verb, adjective, noun, phrasal, idiom);
            display(sentence);
        }
    }

    /////////////////////////////////
    private void startSelectedLevel() {
        cleaner();
        MainView.showMessage("Choose how many sentences you want to:");
        int user = MainView.getUserNum(3000);

        List<Integer> levels = chooseLevelForEachPart();
        int verbLevel = levels.get(0);
        int adjectiveLevel = levels.get(1);
        int nounLevel = levels.get(2);
        int phrasalLevel = levels.get(3);
        int idiomLevel = levels.get(4);

        while (user-- > 0) {
            Grama grama = (Grama) dao.getGramaByID("grama", getRandomInt(PartOptions.GRAMA.getMaxId()).get(0));
            Tense tense = (Tense) dao.getGramaByID("tense", getRandomInt(PartOptions.TENSE.getMaxId()).get(0));
            Type type = (Type) dao.getGramaByID("type", getRandomInt(PartOptions.TYPE.getMaxId()).get(0));
            Person person = dao.createPersonByID(getRandomInt(PartOptions.PERSON.getMaxId()).get(0));
            Verb verb = (Verb) dao.getWordByID("verb", getRandomIntForLevel(PartOptions.VERB.calculateMinMaxLevel(verbLevel).get(0), PartOptions.VERB.calculateMinMaxLevel(verbLevel).get(1)).get(0));
            Adjective adjective = (Adjective) dao.getWordByID("adjective", getRandomIntForLevel(PartOptions.VERB.calculateMinMaxLevel(adjectiveLevel).get(0), PartOptions.VERB.calculateMinMaxLevel(adjectiveLevel).get(1)).get(0));
            Noun noun = (Noun) dao.getWordByID("noun", getRandomIntForLevel(PartOptions.VERB.calculateMinMaxLevel(nounLevel).get(0), PartOptions.VERB.calculateMinMaxLevel(nounLevel).get(1)).get(0));
            Phrasal phrasal = (Phrasal) dao.getWordByID("phrasal", getRandomIntForLevel(PartOptions.VERB.calculateMinMaxLevel(phrasalLevel).get(0), PartOptions.VERB.calculateMinMaxLevel(phrasalLevel).get(1)).get(0));
            Idiom idiom = (Idiom) dao.getWordByID("idiom", getRandomIntForLevel(PartOptions.VERB.calculateMinMaxLevel(idiomLevel).get(0), PartOptions.VERB.calculateMinMaxLevel(idiomLevel).get(1)).get(0));

            Sentence sentence = new Sentence(grama, tense, type, person, verb, adjective, noun, phrasal, idiom);
            display(sentence);
        }
    }

    /////////////////////////////////
    private void startMostLeastRepeated(String leastOrMost) {
        cleaner();
        MainView.showMessage("Choose how many sentences you want to:");
        int user = MainView.getUserNum(95);

        List<Integer> verbID = dao.getMostLeastRepeated(leastOrMost, user, "verb");
        Collections.shuffle(verbID);
        List<Integer> adjectiveID = dao.getMostLeastRepeated(leastOrMost, user, "adjective");
        Collections.shuffle(adjectiveID);
        List<Integer> nounID = dao.getMostLeastRepeated(leastOrMost, user, "noun");
        Collections.shuffle(nounID);
        List<Integer> phrasalID = dao.getMostLeastRepeated(leastOrMost, user, "phrasal");
        Collections.shuffle(phrasalID);
        List<Integer> idiomID = dao.getMostLeastRepeated(leastOrMost, user, "idiom");
        Collections.shuffle(idiomID);

        createSentences(user, verbID, adjectiveID, nounID, phrasalID, idiomID);
    }

    /////////////////////////////////
    private void startByDatetime(String newOrOld) {
        cleaner();
        MainView.showMessage("Choose how many sentences you want to:");
        int user = MainView.getUserNum(95);

        List<Integer> verbID = dao.getByDate(newOrOld, user, "verb");
        Collections.shuffle(verbID);
        List<Integer> adjectiveID = dao.getByDate(newOrOld, user, "adjective");
        Collections.shuffle(adjectiveID);
        List<Integer> nounID = dao.getByDate(newOrOld, user, "noun");
        Collections.shuffle(nounID);
        List<Integer> phrasalID = dao.getByDate(newOrOld, user, "phrasal");
        Collections.shuffle(phrasalID);
        List<Integer> idiomID = dao.getByDate(newOrOld, user, "idiom");
        Collections.shuffle(idiomID);

        createSentences(user, verbID, adjectiveID, nounID, phrasalID, idiomID);

    }

    private void createSentences(int user, List<Integer> verbID, List<Integer> adjectiveID, List<Integer> nounID, List<Integer> phrasalID, List<Integer> idiomID) {
        while (user-- > 0) {
            Grama grama = (Grama) dao.getGramaByID("grama", getRandomInt(PartOptions.GRAMA.getMaxId()).get(0));
            Tense tense = (Tense) dao.getGramaByID("tense", getRandomInt(PartOptions.TENSE.getMaxId()).get(0));
            Type type = (Type) dao.getGramaByID("type", getRandomInt(PartOptions.TYPE.getMaxId()).get(0));
            Person person = dao.createPersonByID(getRandomInt(PartOptions.PERSON.getMaxId()).get(0));

            Verb verb = (Verb) dao.getWordByID("verb", verbID.get(user));
            Adjective adjective = (Adjective) dao.getWordByID("adjective", adjectiveID.get(user));
            Noun noun = (Noun) dao.getWordByID("noun", nounID.get(user));
            Phrasal phrasal = (Phrasal) dao.getWordByID("phrasal", phrasalID.get(user));
            Idiom idiom = (Idiom) dao.getWordByID("idiom", idiomID.get(user));

            Sentence sentence = new Sentence(grama, tense, type, person, verb, adjective, noun, phrasal, idiom);
            display(sentence);
        }
    }

    /////////////////////////////////
    private void startFavorite() {
        cleaner();

        List<Integer> verbID = dao.getByFavorite("verb");
        Collections.shuffle(verbID);
        List<Integer> adjectiveID = dao.getByFavorite("adjective");
        Collections.shuffle(adjectiveID);
        List<Integer> nounID = dao.getByFavorite("noun");
        Collections.shuffle(nounID);
        List<Integer> phrasalID = dao.getByFavorite("phrasal");
        Collections.shuffle(phrasalID);
        List<Integer> idiomID = dao.getByFavorite("idiom");
        Collections.shuffle(idiomID);

        createSentences(1, verbID, adjectiveID, nounID, phrasalID, idiomID);
    }

}
