package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Idiom extends AbstractModel {

    private String explain;
    private String example;

    public Idiom(int id, String eng, String pl, int used, LocalDate localDate, LocalTime localTime, int favorite, String explain, String example) {
        super(id, eng, pl, used, localDate, localTime, favorite);
        this.explain = explain;
        this.example = example;
    }
}
