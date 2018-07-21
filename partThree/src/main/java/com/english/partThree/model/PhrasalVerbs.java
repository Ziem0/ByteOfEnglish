package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class PhrasalVerbs extends AbstractModel {

    private String explain;
    private String example;

    public PhrasalVerbs(int id, String eng, String pl, int used, LocalDate localDate, LocalTime localTime, int favorite, String explain, String example) {
        super(id, eng, pl, used, localDate, localTime, favorite);
        this.explain = explain;
        this.example = example;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
