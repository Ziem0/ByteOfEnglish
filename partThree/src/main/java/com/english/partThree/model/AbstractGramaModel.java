package com.english.partThree.model;

public abstract class AbstractGramaModel {

    private int id;
    private String name;
    private int used;

    public AbstractGramaModel(int id, String name, int used) {
        this.id = id;
        this.name = name;
        this.used = used;
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
}
