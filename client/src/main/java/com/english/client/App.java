package com.english.client;

import com.english.client.enums.Menu;
import com.english.partThree.controller.LearnController;
import com.english.partTwo.View.MainView;
import com.english.partTwo.controller.MainController;
import com.english.partTwo.enums.Colors;

import java.io.IOException;

public class App {

    private static final int GRAMA = 1;
    private static final int WORDS_3000 = 2;
    private static final int MIXED_SENTENCES = 3;
    private static final int CLOSE_PROGRAM = 4;

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf("author:Ziemo Andrzejewski\nversion:1.0.0.\nreleased:17.07.2018\n\n");
            System.out.println(Colors.CYAN.getBg("\t\t\t\t\t\t\t\tENGLISH TEACHER\n\n"));
            System.out.println("Lets start!\n");
            Menu.print();
            System.out.println("\nChoose module:");
            int userIn = MainView.getUserNum(Menu.values().length);

            switch (userIn) {
                case GRAMA:
                    try {
                        new com.english.partOne.app.App();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case WORDS_3000:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    new MainController();
                    break;
                case MIXED_SENTENCES:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    new LearnController();
                    break;
                case CLOSE_PROGRAM:
//                    System.out.println(new String(new char[50]).replace("\0", "\r\n"));
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Goodbye!");
                    run = false;
                    System.exit(1);
                    break;
            }
        }
    }
}
