package com.english.partThree.enums;

public enum EditablePart {

    VERB (20),
    ADJECTIVE (4),
    NOUN (30),
    PHRASAL (5),
    IDIOM (10);

    private int levels;

    EditablePart(int levels) {
        this.levels = levels;
    }

    public int getLevels() {
        return levels;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static void print() {
        for (EditablePart e : EditablePart.values()) {
            System.out.println(String.format("%d: %s",e.ordinal()+1,e));
        }
    }

}
