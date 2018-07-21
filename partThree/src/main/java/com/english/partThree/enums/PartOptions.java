package com.english.partThree.enums;

import com.english.partThree.dao.SentenceDao;

import java.util.LinkedList;
import java.util.List;

public enum PartOptions {

    PERSON(SentenceDao.getDao().getMaxId("person"),1),
    VERB(SentenceDao.getDao().getMaxId("verb"),20),
    NOUN(SentenceDao.getDao().getMaxId("noun"),30),
    ADJECTIVE(SentenceDao.getDao().getMaxId("adjective"),4),
    IDIOM(SentenceDao.getDao().getMaxId("idiom"),10),
    PHRASAL_VERB(SentenceDao.getDao().getMaxId("phrasal"),5),
    GRAMA(SentenceDao.getDao().getMaxId("grama"),1),
    TENSE(SentenceDao.getDao().getMaxId("tense"),1),
    TYPE(SentenceDao.getDao().getMaxId("type"),1),;

    private int maxId;
    private int amountOfLevels;

    PartOptions(int maxId, int amountOfLevels) {
        this.maxId = maxId;
        this.amountOfLevels = amountOfLevels;
    }

    public int getMaxId() {
        return maxId;
    }

    public int getAmountOfLevels() {
        return amountOfLevels;
    }

    public List<Integer> calculateMinMaxLevel(Integer selectedLevel) {
        List<Integer> values = new LinkedList<>();
        int max = 0;

        int amountForLevel = Math.floorDiv(maxId, amountOfLevels);
        int min = selectedLevel * (amountForLevel - 1);

        if (selectedLevel == amountForLevel) {
            max = amountForLevel;
        } else {
            max = selectedLevel * (amountForLevel);
        }

        values.add(min);
        values.add(max);
        return values;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.ordinal() + 1);
    }

    public static void print() {
        for (PartOptions i : PartOptions.values()) {
            System.out.println(i);
        }
    }
}
