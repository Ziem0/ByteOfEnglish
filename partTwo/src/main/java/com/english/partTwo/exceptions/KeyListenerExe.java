package com.english.partTwo.exceptions;

import com.english.partTwo.View.MainView;
import com.english.partTwo.dao.WordsDao;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class KeyListenerExe extends JFrame implements java.awt.event.KeyListener {

    private JLabel label;
    private WordsDao dao;
    private String searchingWord;
    private String translateTypeChecker;
//    private IMenuPrintable enumMenu;


    public KeyListenerExe(WordsDao wordsDao, String translateTypeChecker) {
        super();
        JPanel p = new JPanel();
        label = new JLabel("Key Listener!");
        p.add(label);
        add(p);
        addKeyListener(this);
        setSize(200, 100);
        setVisible(true);
        this.dao = wordsDao;
        this.searchingWord = "";
        this.translateTypeChecker = translateTypeChecker;
    }

//    public KeyListenerExe(IMenuPrintable enumMenu) {
//        super();
//        JPanel p = new JPanel();
//        label = new JLabel("Key Listener!");
//        p.add(label);
//        add(p);
//        addKeyListener(this);
//        setSize(200, 100);
//        setVisible(true);
//        this.enumMenu = enumMenu;
//    }

    @Override
    public void keyTyped(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//            System.out.println("Right key typed");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//            System.out.println("Left key typed");
//        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean updateBoolean;
//        if (enumMenu == null && e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN) {
        searchingWord += String.valueOf(e.getKeyChar());
        if (translateTypeChecker.equals("eng_pl")) {
            updateBoolean = dao.getEngWordsStartingWith(searchingWord);
        } else {
            updateBoolean = dao.getPlWordsStartingWith(searchingWord);
        }
        MainView.showMessage("\nAdd a letter: " + searchingWord + "..\n");
        setVisible(updateBoolean);
        if (!updateBoolean) {
            System.out.println("Searching word(s) doesn't exist.\nEnter to continue..");
//            }
        }

//        if (enumMenu != null && e.getKeyCode() == KeyEvent.VK_UP) {
//            enumMenu.printMenu("up");
//        }
//
//        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//            enumMenu.printMenu("down");
//        }
//
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            setVisible(false);
//            System.out.println("\npress Enter");
//        }
    }
}
