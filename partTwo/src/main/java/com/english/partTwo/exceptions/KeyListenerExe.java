package com.english.partTwo.exceptions;

import com.english.partTwo.View.MainView;
import com.english.partTwo.dao.WordsDao;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class KeyListenerExe extends JFrame implements java.awt.event.KeyListener {

//    private JLabel label;
    private WordsDao dao;
    private String searchingWord;
    private String translateTypeChecker;

    public KeyListenerExe(WordsDao wordsDao, String translateTypeChecker) {
        super("Type a letter(s)");
//        JPanel p = new JPanel();
//        label = new JLabel("Key Listener!");
//        p.add(label);
//        add(p);
        addKeyListener(this);
        setSize(200, 1);
        setVisible(true);
        this.dao = wordsDao;
        this.searchingWord = "";
        this.translateTypeChecker = translateTypeChecker;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean updateBoolean = true;
        searchingWord += String.valueOf(e.getKeyChar());

        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
        if (translateTypeChecker.equals("eng_pl")) {
            updateBoolean = dao.getEngWordsStartingWith(searchingWord);
        } else if (translateTypeChecker.equals("pl_eng")) {
            updateBoolean = dao.getPlWordsStartingWith(searchingWord);
        }

        MainView.showMessage("\nAdd a letter to --> " + searchingWord + "..  \nor ENTER to return");
        setVisible(updateBoolean);

        if (!updateBoolean) {
            System.out.println("\nSearching word(s) doesn't exist.\nPress ENTER to continue..");
        }
    }
}
