package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class AbstractModel {

    private int id;
    private String eng;
    private String pl;
    private int used;
    private LocalDate localDate;
    private LocalTime localTime;
    private int favorite;

    public AbstractModel(int id, String eng, String pl, int used, LocalDate localDate, LocalTime localTime, int favorite) {
        this.id = id;
        this.eng = eng;
        this.pl = pl;
        this.used = used;
        this.localDate = localDate;
        this.localTime = localTime;
        this.favorite = favorite;
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

    public int getUsed() {
        return used;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public void setFavorite(int favorite) {
        if (favorite == 0) {
            favorite++;
        } else if (favorite == 1) {
            favorite--;
        }
    }

    public void setUsed(int used) {
        this.used++;
    }
}
