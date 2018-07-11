package com.english.partOne.controllers;

import com.english.partOne.UI.Command;
import com.english.partOne.dao.Saver;
import com.english.partOne.models.Translator;
import com.english.partOne.models.TranslatorFunctions;

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

            if ("0".equals(choice)) {
                quit = true;

            } else if ("1".equals(choice)) {
                String eng = JOptionPane.showInputDialog("Enter english sentence: ");
                String pol = JOptionPane.showInputDialog("Enter polish sentence: ");
                translator.addNewSentence(eng, pol);
                JOptionPane.showMessageDialog(null, Command.ADDED_MESSAGE);
                translator.saveSentencesToFile(dao);

            } else if ("2".equals(choice)) {
                translator.printGlossary();
                String number = JOptionPane.showInputDialog("Enter index to remove specific sentence: ");
                translator.removeSentence(Integer.valueOf(number));
                translator.saveSentencesToFile(dao);

            } else if ("3".equals(choice)) {
                String lastX = JOptionPane.showInputDialog("Enter how many last sentence you want to repeat: ");
                translator.repeatLastXSentences(Integer.valueOf(lastX));
                runTranslator(translator.getLastXSentences());

            } else if ("4".equals(choice)) {
                translator.randomListSorting();
                runTranslatorUnchecked(translator.getGlossary());
                translator.standardListSorting();
                translator.saveSentencesToFile(dao);

                //for test
            } else if ("5".equals(choice)) {
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
