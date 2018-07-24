package com.english.partThree.model;

import com.english.partThree.dao.SentenceDao;
import com.english.partTwo.View.MainView;

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

    public void setEng(String engU) {
        this.eng = engU;
    }

    public void setPl(String plU) {
        this.pl = plU;
    }

    public void setLocalDate() {
        this.localDate = LocalDate.now();
    }

    public void setLocalTime() {
        this.localTime = LocalTime.now();
    }

    public void setFavorite() {
        if (favorite == 0) {
            favorite++;
        } else if (favorite == 1) {
            favorite--;
        }
    }

    public void setUsed() {
        this.used++;
    }

    public void upgrade(SentenceDao dao) {
        setLocalDate();
        setLocalTime();
        setUsed();
        dao.updateWord(this);
    }

    public void modify() {
        System.out.printf("change '%s' on --> ", eng);
        String userENG = MainView.getUserString();
        setEng(userENG);

        System.out.printf("change '%s' on --> ", pl);
        String userPL = MainView.getUserString();
        setPl(userPL);
    }

    @Override
    public String toString() {
        return String.format("%s", eng);
    }

}

