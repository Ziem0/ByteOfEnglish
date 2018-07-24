package com.english.partThree.model;

import com.english.partThree.interfaces.ExAble;
import com.english.partTwo.View.MainView;

import java.time.LocalDate;
import java.time.LocalTime;

public class Idiom extends AbstractModel implements ExAble {

    private String explain;
    private String example;

    public Idiom(int id, String eng, String pl, int used, LocalDate localDate, LocalTime localTime, int favorite, String explain, String example) {
        super(id, eng, pl, used, localDate, localTime, favorite);
        this.explain = explain;
        this.example = example;
    }

    public String getExplain() {
        return explain;
    }

    public String getExample() {
        return example;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public void modify() {
        super.modify();
        System.out.printf("change '%s' on --> ", explain);
        String userExplain = MainView.getUserString();
        setExplain(userExplain);

        System.out.printf("change '%s' on --> ", example);
        String userExample = MainView.getUserString();
        setExample(userExample);
    }
}
