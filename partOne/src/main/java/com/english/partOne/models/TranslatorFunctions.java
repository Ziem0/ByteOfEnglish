package com.english.partOne.models;

import com.english.partOne.dao.Saver;
import java.io.FileNotFoundException;
import java.util.List;

public interface TranslatorFunctions{
    void addNewSentence(String newSentence, String newSentencePol);
    void removeSentence(int number);
    void repeatLastXSentences(int x);
    void randomListSorting();
    void standardListSorting();
    void changeAllSentencesIsDone();
    void printGlossary();
    List<Translator.Sentence> getLastXSentences();
    void saveSentencesToFile(Saver saver) throws FileNotFoundException;
    List<Translator.Sentence> getGlossary();
    }




