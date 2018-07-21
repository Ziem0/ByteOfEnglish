package com.english.partThree.model;

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

    public void setUsed(int used) {
        this.used++;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public String toString() {
        return String.format("%d %s %d %s %s", id, name, used, localDate, localTime);
    }
}
