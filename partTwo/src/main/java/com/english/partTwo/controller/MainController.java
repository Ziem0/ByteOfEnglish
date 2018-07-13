package com.english.partTwo.controller;

import com.english.partTwo.View.MainView;
import com.english.partTwo.dao.DataBaseConnection;
import com.english.partTwo.dao.WordsDao;
import com.english.partTwo.enums.*;
import com.english.partTwo.models.Word;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MainController {

    private WordsDao dao;

    public MainController() {
        this.dao = WordsDao.getDao();
        DataBaseConnection.getMigration();
//        dao.loadCSV();
        startController();
    }

    private void startController() {
        boolean runningApp = true;
        while (runningApp) {
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
                default:
                    runningApp = false;
            }
        }
    }

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
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    runningLearnOptions = false;
            }
        }
    }

    private void startLastDaysWords() {
        
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
        int middleUnknownAmount = Math.floorDiv(userAmount, 2);
        int knownAmount = Math.floorDiv(middleUnknownAmount, 2);
        waitingProcess(userAmount,middleUnknownAmount,knownAmount);

        HashMap<Integer, List<Integer>> unknownIDs = dao.getAllUnknownID();
        HashMap<Integer, List<Integer>> middleKnownIDs = dao.getAllMiddleknownID();
        HashMap<Integer, List<Integer>> knownIDs = dao.getAllKnownID();

        if (userAmount != 0) { runMixedStatus(userAmount, unknownIDs); }
        if (middleUnknownAmount != 0) { runMixedStatus(middleUnknownAmount, middleKnownIDs); }
        if (knownAmount != 0) { runMixedStatus(knownAmount, knownIDs); }
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

    private void runMixedStatus(int userAmount, HashMap<Integer, List<Integer>> listIDs) {
        List<Integer> finalList = new LinkedList<>();
        adding:
        for (int key : listIDs.keySet()) {
            Collections.shuffle(listIDs.get(key)); ;
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
                MainView.showMessage(w.getEng());
                sc.nextLine();
                MainView.showMessage(w.getPl());
                System.out.println(w);
                break;
            case "pl":
                MainView.showMessage(w.getPl());
                sc.nextLine();
                MainView.showMessage(w.getEng());
                System.out.println(w);
                break;
        }
        updateWord(w);
    }

    private void updateWord(Word w) {
        w.updateDate();
        w.updateHour();
        w.updateRepeated();
        UpdateOptions.printMenu();
        String userChoice = MainView.getUserString();

        switch (userChoice) {
            case "":
                dao.updateWord(w);
                break;
            case "1":
                w.increaseStatus();
                dao.updateWord(w);
                break;
            case "2":
                w.decreaseStatus();
                dao.updateWord(w);
                break;
            case "3":
                w.setTrashStatus();
                dao.updateWord(w);
                break;
            case "4":
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
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    runningStatisticOptions = false;
            }
        }
    }

    public static void main(String[] args) {
        MainController control = new MainController();
    }


//find algorithm for random with date
//admin options for amend data
//enum for eng-pol

}
