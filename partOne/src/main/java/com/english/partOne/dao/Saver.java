package com.english.partOne.dao;

import java.io.FileNotFoundException;
import java.util.List;

public interface Saver {
    void saveSentences(List<String> newListSentences, List<String> newListSentencesChecker) throws FileNotFoundException;
}
