package com.english.partTwo.controller;

import com.english.partTwo.View.MainView;
import com.english.partTwo.dao.DataBaseConnection;
import com.english.partTwo.dao.WordsDao;
import com.english.partTwo.enums.*;
import com.english.partTwo.exceptions.KeyListenerExe;
import com.english.partTwo.models.Word;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MainController {

    private WordsDao dao;

    public MainController() {
        this.dao = WordsDao.getDao();
        DataBaseConnection.getMigration();
        dao.loadCSV();
        System.out.println("Loading data...\nPlease wait...\n");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startStatusStatistic();
        startController();
    }

    private void startController() {
        boolean run = true;
        while (run) {
            MainMenu.printMenu();
            MainView.showMessage("Choose option: ");
            int userOption = MainView.getUserNum(MainMenu.values().length);
            switch (userOption) {
                case 1:
                    learnController();
                    break;
                case 2:
                    statisticController();
                    break;
                case 3:
                    amendController();
                    break;
                case 4:
                    run = false;
                    break;
            }
        }
    }

    private void amendController() {
        boolean isRunning = true;

        while (isRunning) {
            AmendOptions.print();
            MainView.showMessage("Choose option: ");
            int user = MainView.getUserNum(AmendOptions.values().length);
            switch (user) {
                case 1:
                    addWord();
                    break;
                case 2:
                    deleteWord();
                    break;
                case 3:
                    isRunning = false;
                    break;
            }
        }
    }

    private void deleteWord() {
        System.out.println("First find word's ID");
        this.startShowByLetter();

        System.out.println("Enter word's ID to delete: ");
        int selectedID = MainView.getUserNum(dao.getLastID());   //--> add back
        boolean isDeleted = dao.deleteWord(selectedID);
        if (isDeleted) {
            System.out.println("Done! Selected word has been deleted");
        } else {
            System.out.println("Incorrect ID!");
        }
    }

    private void addWord() {
        MainView.showMessage("Enter ENG sentence: ");
        String eng = MainView.getUserString();
        MainView.showMessage("Enter PL sentence: ");
        String pl = MainView.getUserString();
        boolean isDone = dao.addWord(new Word(eng, pl));
        if (isDone) {
            System.out.println("Done! New word has been added");
        } else {
            System.out.println("Incorrent input!");
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void learnController() {
        boolean runningLearnOptions = true;

        while (runningLearnOptions) {
            LearnOptions.printMenu();
            MainView.showMessage("Choose option: ");

            int userOption2 = MainView.getUserNum(LearnOptions.values().length);
            switch (userOption2) {
                case 1:
                    startGeneralRandom();
                    break;
                case 2:
                    startMixedStatus();
                    break;
                case 3:
                    startLastDaysWords();
                    break;
                case 4:
                    startLastWords();
                    break;
                case 5:
                    startWordsAs("unknown");
                    break;
                case 6:
                    startWordsAs("middleKnown");
                    break;
                case 7:
                    startWordsAs("known");
                    break;
                case 8:
                    runningLearnOptions = false;
                    break;
            }
        }
    }

    private boolean startWordsAs(String status) {
        if (dao.getSelectedIdStatusAmount(status) == 0) {
            MainView.showMessage("There are no words with status " + status);
            try {
                Thread.sleep(2000);
                MainView.showMessage("Processing.. Please wait..");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        MainView.showMessage("Enter how many last used " + status + " words you want to: ");
        int userAmount = MainView.getUserNum(dao.getSelectedIdStatusAmount(status));
        List<Integer> wordsIDs = dao.getSelectedIdStatusList(status);
        Collections.shuffle(wordsIDs);
        wordsIDs = wordsIDs.subList(0, userAmount);

        TranslateOptions.printMenu();
        MainView.showMessage("Choose option: ");

        chooseTranslateOption(wordsIDs);
        return true;
    }

    private void startLastWords() {
        MainView.showMessage("Enter how many last used words you want to: ");
        int userAmount = MainView.getUserNum(dao.getLastID());
        List<Integer> wordsIDs = dao.getLastWords(userAmount);

        TranslateOptions.printMenu();
        MainView.showMessage("Choose option: ");

        chooseTranslateOption(wordsIDs);
    }

    private void startLastDaysWords() {
        MainView.showMessage("Enter how many days you want to get back: ");
        int maxDaysBack = Period.between(dao.getOldestDate(), LocalDate.now()).getDays();
        int userAmount = MainView.getUserNum(maxDaysBack);

        LocalDate userOldestDate = LocalDate.now().minusDays(userAmount);
        String userOldestDateString = userOldestDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Integer> wordsIDs = dao.getWordsFromLastDays(userOldestDateString);

        TranslateOptions.printMenu();
        MainView.showMessage("Choose option: ");

        chooseTranslateOption(wordsIDs);
    }

    private void startGeneralRandom() {
        MainView.showMessage("Enter how many words you want to: ");
        int userAmount = MainView.getUserNum(dao.getLastID());
        List<Integer> randomIDs = new LinkedList<>();
        ThreadLocalRandom.current().ints(1, dao.getLastID())
                .distinct()
                .limit(userAmount)
                .forEach(randomIDs::add);
        TranslateOptions.printMenu();
        MainView.showMessage("Choose option: ");

        chooseTranslateOption(randomIDs);
    }

    private void startMixedStatus() {
        MainView.showMessage("Enter how many words you want for 'UNKNOWN' words : ");
        int userAmount = MainView.getUserNum(dao.getLastID());
        int middleKnownAmount = Math.floorDiv(userAmount, 2);
        int knownAmount = Math.floorDiv(middleKnownAmount, 2);
        waitingProcess(userAmount, middleKnownAmount, knownAmount);

        HashMap<Integer, List<Integer>> unknownIDs = dao.getAllUnknownID();
        HashMap<Integer, List<Integer>> middleKnownIDs = dao.getAllMiddleKnownID();
        HashMap<Integer, List<Integer>> knownIDs = dao.getAllKnownID();

        if (userAmount != 0) {
            runMixedStatus(userAmount, unknownIDs);
        }
        if (middleKnownAmount != 0) {
            runMixedStatus(middleKnownAmount, middleKnownIDs);
        }
        if (knownAmount != 0) {
            runMixedStatus(knownAmount, knownIDs);
        }
    }

    private void waitingProcess(int unknown, int middleKnown, int known) {
        System.out.println("Calculating words proportion for each status...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Done! Proportion is following: unknown:%d middleKnown:%d known:%d\n", unknown, middleKnown, known);
    }

    /*
   Sorted by 'repeated' and in the end shuffle list
     */
    private void runMixedStatus(int userAmount, HashMap<Integer, List<Integer>> listIDs) {
        List<Integer> finalList = new LinkedList<>();
        adding:
        for (int key : listIDs.keySet()) {
            Collections.shuffle(listIDs.get(key));
            for (int value : listIDs.get(key)) {
                finalList.add(value);
                userAmount--;
                if (userAmount < 1) {
                    break adding;
                }
            }
        }
        TranslateOptions.printMenu();
        MainView.showMessage("Choose option: ");

        chooseTranslateOption(finalList);
    }

    private void chooseTranslateOption(List<Integer> finalList) {
        int userOption4 = MainView.getUserNum(TranslateOptions.values().length);
        for (int i : finalList) {
            switch (userOption4) {
                case 1:
                    Word w = dao.getWordByID(i);
                    display(w, "eng");
                    break;
                case 2:
                    Word wa = dao.getWordByID(i);
                    display(wa, "pl");
                    break;
            }
        }
    }

    private void display(Word w, String starterLanguage) {
        Scanner sc = new Scanner(System.in);
        switch (starterLanguage) {
            case "eng":
                MainView.showMessage(Colors.MAGENTA.getBg(w.getEng()));
                sc.nextLine();
                MainView.showMessage(Colors.GREEN.getBg(w.getPl()));
                System.out.println(w);
                break;
            case "pl":
                MainView.showMessage(Colors.MAGENTA.getBg(w.getPl()));
                sc.nextLine();
                MainView.showMessage(Colors.GREEN.getBg(w.getEng()));
                System.out.println(w);
                break;
        }
        try {
            updateWord(w);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateWord(Word w) throws InterruptedException {
        w.updateDate();
        w.updateHour();
        w.updateRepeated();
        UpdateOptions.printMenu();
        String userChoice = MainView.getUserString().toUpperCase();

        switch (userChoice) {
            case "":
                dao.updateWord(w);
                break;
            case "U":
                w.increaseStatus();
                Thread.sleep(1000);
                dao.updateWord(w);
                break;
            case "D":
                w.decreaseStatus();
                Thread.sleep(1000);
                dao.updateWord(w);
                break;
            case "T":
                w.setTrashStatus();
                Thread.sleep(1000);
                dao.updateWord(w);
                break;
            case "E":
                System.out.printf("Change '%s' to --> ", w.getEng());
                String newEng = MainView.getUserString();
                w.setEng(newEng);
                System.out.printf("Change '%s' to --> ", w.getPl());
                String newPl = MainView.getUserString();
                w.setPl(newPl);
                dao.updateWord(w);
                break;
            default:
                dao.updateWord(w);
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void statisticController() {
        boolean runningStatisticOptions = true;
        while (runningStatisticOptions) {
            StatisticOptions.printMenu();
            MainView.showMessage("Choose option: ");

            int userOption3 = MainView.getUserNum(StatisticOptions.values().length);
            switch (userOption3) {
                case 1:
                    startStatusStatistic();
                    break;
                case 2:
                    startShowByLetter();
                    break;
                case 3:
                    startShowMostRepeated();
                    break;
                case 4:
                    startShowLeastReapeted();
                    break;
                case 5:
                    runningStatisticOptions = false;
            }
        }
    }

    private void startStatusStatistic() {
        StatusStatistic.print();
    }

    private void startShowLeastReapeted() {
        dao.get10LeastRepeated().forEach(x->{
            System.out.println(x);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void startShowMostRepeated() {
        dao.get10MostRepeated().forEach(x->{
            System.out.println(x);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        KeyListenerExe exe = new KeyListenerExe(StatisticOptions.EXIT);
//        MainView.getUserString();
//
//        int userChoice = StatisticOptions.getCounter() + 1;
//        switch (userChoice) {
//            case 1:
//                System.out.println("jedyneczka");
//                break;
//            case 2:
//                System.out.println("dwojeczka");
//                break;
//            case 3:
//                System.out.println("trojeczka");
//                break;
//            case 4:
//                System.out.println("czwora");
//                break;
//        }

    }

    private void startShowByLetter() {
        TranslateOptions.printMenu();
        int userOption4 = MainView.getUserNum(TranslateOptions.values().length);
        switch (userOption4) {
            case 1:
                MainView.showMessage("Add a letter to: ..");
                new KeyListenerExe(dao, "eng_pl");
                MainView.getUserString();
                break;
            case 2:
                MainView.showMessage("Add a letter to: ..");
                new KeyListenerExe(dao, "pl_eng");
                MainView.getUserString();
                break;
        }
    }

    public static void main(String[] args) {
        new MainController();
    }
}
