package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Verb extends AbstractModel {

    private String simple;
    private String perfect;

    public Verb(int id, String eng, String pl, int used, LocalDate localDate, LocalTime localTime, int favorite, String simple, String perfect) {
        super(id, eng, pl, used, localDate, localTime, favorite);
        this.simple = simple;
        this.perfect = perfect;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

    public String getPerfect() {
        return perfect;
    }

    public void setPerfect(String perfect) {
        this.perfect = perfect;
    }

    @Override
    public String toString() {
        return String.format("%d %s %s %s %d %s %s %d", getId(), getEng(), simple, perfect, getUsed(), getLocalDate(), getLocalTime(), getFavorite());
    }
}
