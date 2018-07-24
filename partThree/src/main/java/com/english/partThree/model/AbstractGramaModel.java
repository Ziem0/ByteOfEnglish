package com.english.partThree.model;

import com.english.partThree.dao.SentenceDao;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class AbstractGramaModel {

    private int id;
    private String name;
    private int used;
    private LocalDate localDate;
    private LocalTime localTime;

    public AbstractGramaModel(int id, String name, int used, LocalDate localDate, LocalTime localTime) {
        this.id = id;
        this.name = name;
        this.used = used;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed() {
        this.used++;
    }

    public void setLocalDate() {
        this.localDate = LocalDate.now();
    }

    public void setLocalTime() {
        this.localTime = LocalTime.now();
    }

    public void upgrade(SentenceDao dao) {
        setLocalDate();
        setLocalTime();
        setUsed();
        dao.updateGrama(this);
    }

    @Override
    public String toString() {
        return String.format("%s",name);
    }
}
