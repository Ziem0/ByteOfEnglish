package controllers;

import UI.Command;
import dao.Saver;
import models.Translator;
import models.TranslatorFunctions;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.List;

public class TranslatorController {
    private TranslatorFunctions translator;
    private Saver dao;

    public TranslatorController(TranslatorFunctions translator, Saver dao) {
        this.translator = translator;
        this.dao = dao;
    }

    public void start() throws FileNotFoundException {
        boolean quit = false;
        while (!quit) {
            translator.changeAllSentencesIsDone();
            String choice;
            JOptionPane.showMessageDialog(null, "Lets begin our daily practice!");
            JOptionPane.showMessageDialog(null, Command.OPTIONS);
            choice = JOptionPane.showInputDialog("Choose option what would you like to do:");

            switch (choice) {
                case "0":
                    quit = true;
                    break;
                case "1":
                    String eng = JOptionPane.showInputDialog("Enter english sentence: ");
                    String pol = JOptionPane.showInputDialog("Enter polish sentence: ");
                    translator.addNewSentence(eng, pol);
                    JOptionPane.showMessageDialog(null, Command.ADDED_MESSAGE);
                    translator.saveSentencesToFile(dao);
                    break;
                case "2":
                    translator.printGlossary();
                    String number = JOptionPane.showInputDialog("Enter index to remove specific sentence: ");
                    translator.removeSentence(Integer.valueOf(number));
                    translator.saveSentencesToFile(dao);
                    break;
                case "3":
                    String lastX = JOptionPane.showInputDialog("Enter how many last sentence you want to repeat: ");
                    translator.repeatLastXSentences(Integer.valueOf(lastX));
                    runTranslator(translator.getLastXSentences());
                    break;
                case "4":
                    translator.randomListSorting();
                    runTranslatorUnchecked(translator.getGlossary());
                    translator.standardListSorting();
                    translator.saveSentencesToFile(dao);
                    break;
                //for test
                case "5":
                    for (Translator.Sentence i : translator.getGlossary()) {
                        System.out.println(i.getIsDone());
                    }
            }
        }
    }

    private void runTranslator(List<Translator.Sentence> list) {
        for (Translator.Sentence sentence : list) {
            JOptionPane.showMessageDialog(null, sentence.getPol());
            
            JOptionPane.showMessageDialog(null, sentence.getEng());
            String choice = JOptionPane.showInputDialog("Q to quit, " + Command.ENTER_CONTINUE);
            if (choice.matches("[qQ]{1}")) {
                break;
            }
        }
    }
    private void runTranslatorUnchecked(List<Translator.Sentence> list) {
        int counter = 1;
        for (Translator.Sentence sentence : list) {
            if (!sentence.getIsDone()) {
                JOptionPane.showMessageDialog(null, sentence.getPol());
                
                JOptionPane.showMessageDialog(null, sentence.getEng());
                sentence.isDoneTrue();
                System.out.println(counter);
                String choice = JOptionPane.showInputDialog("Q to quit, " + Command.ENTER_CONTINUE);
                if (choice.matches("[qQ]{1}")) {
                    break;
                }
                counter++;
            }
        }
    }
}
