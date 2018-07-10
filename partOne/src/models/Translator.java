package models;

import dao.Saver;

import java.io.FileNotFoundException;
import java.util.*;

/**dsd
 * created by Ziemo Andrzejewski on 01/02/2018
 */
public class Translator implements SentenceRegistry, TranslatorFunctions {
    private static int idx = 1;
    private List<Sentence> glossary;
    private List<Translator.Sentence> lastXSentences;
    private static final Comparator<Sentence> RANDOM_ORDER;
    private static final Comparator<Sentence> NUMBER_INCREASE_ORDER;

    static{
        RANDOM_ORDER = new Comparator<Sentence>() {
            @Override
            public int compare(Sentence o1, Sentence o2) {
                Random random = new Random();
                if (o1.number > o2.number) {
                    return random.nextInt(2)-1;
                } else if (o1.number < o2.number) {
                    return random.nextInt(2)-1;
                }
                return random.nextInt(2)-1;
            }
    };}

    static {
        NUMBER_INCREASE_ORDER = new Comparator<Sentence>() {
            @Override
            public int compare(Sentence sentence1, Sentence sentence2) {
                if (sentence1.number > sentence2.number) {
                    return 1;
                } else if (sentence1.number < sentence2.number) {
                    return -1;
                }
                return 0;
            }
        };
    }

    public Translator(List<String> typeSentences, List<String> typeSentencesChecker) {
        idx = 1;
        this.glossary = new LinkedList<>();
        this.lastXSentences = new LinkedList<>();
        addSentencesToTranslator(typeSentences, typeSentencesChecker);
    }

    @Override
    public List<Sentence> getGlossary() {
        return glossary;
    }

    @Override
    public List<Sentence> getLastXSentences() {
        return lastXSentences;
    }

    @Override
    public void addSentencesToTranslator(List<String> typeSentences, List<String> typeSentencesChecker) {
        for (int i = 0; i < typeSentences.size(); i++) {
            glossary.add(new Sentence(typeSentences.get(i), typeSentencesChecker.get(i)));
        }
        System.out.println("Program added " + typeSentences.size() + " sentences!");
    }

    @Override
    public void addNewSentence(String newSentenceEng, String newSentencePol) {
        String newSentence = String.join(",", newSentenceEng, newSentencePol);
        glossary.add(new Sentence(newSentence, "false"));
    }

    @Override
    public void removeSentence(int number) {
        int foundSentence = Collections.binarySearch(glossary, new Sentence(number), NUMBER_INCREASE_ORDER);
        if (foundSentence >= 0) {
            glossary.remove(foundSentence);
            System.out.println("Sentence was removed!");
        } else {
            System.out.println("There is no sentence no." + number);
        }
    }

    @Override
    public void repeatLastXSentences(int x) {
        this.lastXSentences = null;
        int start = glossary.size() - x;
        lastXSentences = glossary.subList(start, glossary.size());
    }

    private boolean isAllSentencesLearned() {
        for (Sentence sentence : glossary) {
            if (!sentence.isDone) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void changeAllSentencesIsDone() {
        if (isAllSentencesLearned()) {
            for (Sentence sentence : glossary) {
                sentence.isDone = false;
            }
        }
    }

    @Override
    public void randomListSorting() {
        Collections.shuffle(glossary);
    }

    @Override
    public void standardListSorting() {
        Collections.sort(glossary, NUMBER_INCREASE_ORDER);
    }

    @Override
    public void saveSentencesToFile(Saver saver) throws FileNotFoundException {
        LinkedList<String> newListSentences = new LinkedList<>();
        LinkedList<String> newListSentencesChecker = new LinkedList<>();

        for (int i = 0; i < glossary.size(); i++) {
            boolean isDoneSentence = glossary.get(i).isDone;
            String engSentence = glossary.get(i).eng;
            String polSentence = glossary.get(i).pol;
            String newSentence = String.join(",", engSentence, polSentence);
            newListSentences.add(newSentence);
            newListSentencesChecker.add(String.valueOf(isDoneSentence));
        }
        saver.saveSentences(newListSentences, newListSentencesChecker);
        System.out.println("File was successfully updated and saved!");
    }

    @Override
    public void printGlossary() {
        Collections.sort(glossary, NUMBER_INCREASE_ORDER);
        for (Sentence i : glossary) {
            System.out.println(i.number + " --> " + i.eng + "\n\t" + i.pol + "\n");
        }
    }





    public static class Sentence implements Comparable<Sentence> {
        private boolean isDone;
        private String eng;
        private String pol;
        private int number;

        public Sentence(String line, String isDone) {
            String[] lineList = line.split(",");
            this.eng = lineList[0];
            this.pol = lineList[1];
            if (isDone.equals("false")) {
                this.isDone = false;
            } else {
                this.isDone = true;
            }
            this.number = Translator.idx;
            Translator.idx++;
        }

        public Sentence(int number) {
            this.number = number;
        }

        public boolean getIsDone() {
            return isDone;
        }

        public String getEng() {
            return eng;
        }

        public String getPol() {
            return pol;
        }

        public int getNumber() {
            return number;
        }

        public void isDoneTrue() {
            this.isDone = true;
        }

        //decrease sorting
        @Override
        public int compareTo(Sentence sentence) {
            if (this.number > sentence.number) {
                return -1;
            } else if (this.number < sentence.number) {
                return 1;
            }
            return 0;
        }
    }
}

