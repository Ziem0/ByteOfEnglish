package app;

import UI.Command;
import controllers.ControllerInput;
import controllers.TranslatorController;
import dao.Dao;
import models.Translator;
import models.TranslatorFunctions;

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

            switch (user) {
                case "0":
                    JOptionPane.showMessageDialog(null, Command.END);
                    quitProgram = true;
                    break;
                case "1":
                    Dao dao = new Dao(Dao.COMMONLY_SENTENCES, Dao.COMMONLY_SENTENCES_CHECKER);
                    dao.loadSentences();
                    TranslatorFunctions translator = new Translator(dao.getSentences(), dao.getBooleansForSentences());
                    TranslatorController controller = new TranslatorController(translator, dao);
                    controller.start();
                    break;
                case "2":
                    Dao dao2 = new Dao(Dao.IT_SENTENCES, Dao.IT_SENTENCES_CHECKER);
                    dao2.loadSentences();
                    TranslatorFunctions translator2 = new Translator(dao2.getSentences(), dao2.getBooleansForSentences());
                    TranslatorController controller2 = new TranslatorController(translator2, dao2);
                    controller2.start();
                    break;
                case "3":
                    Dao dao3 = new Dao(Dao.GRAMMA_SENTENCES, Dao.GRAMMA_SENTENCES_CHECKER);
                    dao3.loadSentences();
                    TranslatorFunctions translator3 = new Translator(dao3.getSentences(), dao3.getBooleansForSentences());
                    TranslatorController controller3 = new TranslatorController(translator3, dao3);
                    controller3.start();
                    break;
                case "4":
                    String choice = null;
                    do {
                        choice = JOptionPane.showInputDialog("\n1. --> start repeat\n2. --> edit gramma\n");
                    } while (!ControllerInput.InvalidNumberInput(choice));

                    switch (choice) {
                        case "1":
                            Dao.loadGramma();
                            break;
                        case "2":
                            Runtime.getRuntime().exec("gedit src/resources/gramma.csv");
                            break;
                    }
            }
        }
    }
}
