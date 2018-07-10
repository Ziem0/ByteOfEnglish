package models;

import java.util.List;

public interface SentenceRegistry {
    void addSentencesToTranslator(List<String> typeSentences, List<String> typeSentencesChecker);
}
