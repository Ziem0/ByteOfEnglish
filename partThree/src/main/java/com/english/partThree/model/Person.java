package com.english.partThree.model;

public class Person {

    private int id;
    private String eng;
    private String pl;
    private int used;

    public Person(int id, String eng, String pl, int used) {
        this.id = id;
        this.eng = eng;
        this.pl = pl;
        this.used = used;
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

    public void setUsed(int used) {
        this.used++;
    }

    @Override
    public String toString() {
        return String.format("%d %s %d", id, eng, used);
    }
}
