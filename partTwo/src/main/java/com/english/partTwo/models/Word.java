package com.english.partTwo.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Word {

    private int id;
    private String eng;
    private String pl;
    private String status;
    private int statusID;
    private LocalDate date;
    private LocalTime hour;
    private int repeated;

    public Word(int id, String eng, String pl, String status, int statusID, LocalDate date, LocalTime hour, int repeated) {
        this.id = id;
        this.eng = eng;
        this.pl = pl;
        this.status = status;
        this.statusID = statusID;
        this.date = date;
        this.hour = hour;
        this.repeated = repeated;
    }

    public Word(String eng, String pl) {
        this.eng = eng;
        this.pl = pl;
    }

    public int getId() {
        return id;
    }

    public String getEng() {
        return eng;
    }

    public String getPl() {
        return pl;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusID() {
        return statusID;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public int getRepeated() {
        return repeated;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public void increaseStatus() {
        this.statusID++;
    }

    public void decreaseStatus() {
        this.statusID--;
    }

    public void updateDate() {
        this.date = LocalDate.now();
    }

    public void updateHour() {
        this.hour = LocalTime.now();
    }

    public void updateRepeated() {
        this.repeated++;
    }

    @Override
    public String toString() {
        return String.format("id:%s status:%s date:%s repeated:%s\n", id, status, date, repeated);
    }
}
