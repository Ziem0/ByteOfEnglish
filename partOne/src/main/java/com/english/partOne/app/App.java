package com.english.partOne.app;


import com.english.partOne.UI.Command;
import com.english.partOne.controllers.ControllerInput;
import com.english.partOne.controllers.TranslatorController;
import com.english.partOne.dao.Dao;
import com.english.partOne.models.Translator;
import com.english.partOne.models.TranslatorFunctions;

import javax.swing.*;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        boolean quitProgram = false;

        while (!quitProgram) {
            String user;
            JOptionPane.showMessageDialog(null, Command.WELCOME);
            JOptionPane.showMessageDialog(null, Command.MAIN_OPTIONS);
            user = JOptionPane.showInputDialog("Enter number: ");

            if ("0".equals(user)) {
                JOptionPane.showMessageDialog(null, Command.END);
                quitProgram = true;

            } else if ("1".equals(user)) {
                Dao dao = new Dao(Dao.COMMONLY_SENTENCES, Dao.COMMONLY_SENTENCES_CHECKER);
                dao.loadSentences();
                TranslatorFunctions translator = new Translator(dao.getSentences(), dao.getBooleansForSentences());
                TranslatorController controller = new TranslatorController(translator, dao);
                controller.start();

            } else if ("2".equals(user)) {
                Dao dao2 = new Dao(Dao.IT_SENTENCES, Dao.IT_SENTENCES_CHECKER);
                dao2.loadSentences();
                TranslatorFunctions translator2 = new Translator(dao2.getSentences(), dao2.getBooleansForSentences());
                TranslatorController controller2 = new TranslatorController(translator2, dao2);
                controller2.start();

            } else if ("3".equals(user)) {
                Dao dao3 = new Dao(Dao.GRAMMA_SENTENCES, Dao.GRAMMA_SENTENCES_CHECKER);
                dao3.loadSentences();
                TranslatorFunctions translator3 = new Translator(dao3.getSentences(), dao3.getBooleansForSentences());
                TranslatorController controller3 = new TranslatorController(translator3, dao3);
                controller3.start();

            } else if ("4".equals(user)) {
                String choice = null;
                do {
                    choice = JOptionPane.showInputDialog("\n1. --> start repeat\n2. --> edit gramma\n");
                } while (!ControllerInput.InvalidNumberInput(choice));

                if ("1".equals(choice)) {
                    Dao.loadGramma();

                } else if ("2".equals(choice)) {
                    Runtime.getRuntime().exec("gedit src/resources/gramma.csv");

                }
            }
        }
    }
}
