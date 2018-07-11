package com.english.partOne.dao;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Dao implements Loader, Saver {

    public static final String COMMONLY_SENTENCES = "src/resources/commonlySentences.csv";
    public static final String COMMONLY_SENTENCES_CHECKER = "src/resources/commonlySentencesChecker.csv";
    public static final String GRAMMA_SENTENCES = "src/resources/grammaSentences.csv";
    public static final String GRAMMA_SENTENCES_CHECKER = "src/resources/grammaSentencesChecker.csv";
    public static final String IT_SENTENCES = "src/resources/itSentences.csv";
    public static final String IT_SENTENCES_CHECKER = "src/resources/itSentencesChecker.csv";
    public static final String GRAMMA = "src/resources/gramma.csv";

    private List<String> sentences;
    private List<String> booleansForSentences;
    private String typeSentences;
    private String typeSentencesChecker;

    public List<String> getSentences() {
        return sentences;
    }

    public List<String> getBooleansForSentences() {
        return booleansForSentences;
    }

    public Dao(String typeSentences, String typeSentencesChecker) {
        this.sentences = new LinkedList<>();
        this.booleansForSentences = new LinkedList<>();
        this.typeSentences = typeSentences;
        this.typeSentencesChecker = typeSentencesChecker;
    }

    @Override
    public void loadSentences() throws IOException {
        FileReader fr1 = null;
        FileReader fr2 = null;
        String line1 = "";
        String line2 = "";

        fr1 = new FileReader(typeSentences);
        fr2 = new FileReader(typeSentencesChecker);

        BufferedReader bf1 = new BufferedReader(fr1);
        BufferedReader bf2 = new BufferedReader(fr2);

        while ((line1 = bf1.readLine()) != null && (line2 = bf2.readLine()) != null) {
            sentences.add(line1);
            booleansForSentences.add(line2);
        }
        bf1.close();
        bf2.close();
        fr1.close();
        fr2.close();
    }

    @Override
    public void saveSentences(List<String> newListSentences, List<String> newListSentencesChecker) throws FileNotFoundException {
        PrintWriter writer1 = null;
        PrintWriter writer2 = null;

        writer1 = new PrintWriter(typeSentences);
        writer2 = new PrintWriter(typeSentencesChecker);

        for (int i = 0; i < newListSentences.size(); i++) {
            writer1.println(newListSentences.get(i));
            writer2.println(newListSentencesChecker.get(i));
        }
        writer1.close();
        writer2.close();
    }

    public static void loadGramma() throws IOException {
        String line = "";
        FileReader fr = null;
        fr = new FileReader(GRAMMA);

        BufferedReader br = new BufferedReader(fr);

        while ((line = br.readLine()) != null) {

            if (!line.isEmpty() && line.contains("@")) {
                JOptionPane.showMessageDialog(null, line);
                System.out.print("\033[H\033[2J\n\n");
                System.out.flush();
            } else {
                System.out.println(line);
            }
        }
        br.close();
        fr.close();
    }
}
