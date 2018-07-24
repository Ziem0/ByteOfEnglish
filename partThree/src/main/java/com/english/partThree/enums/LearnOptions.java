package com.english.partThree.enums;

public enum LearnOptions {

    ALL("<x> sentences with full range"),
    LEVEL_LIMITED("<x> sentences limited by level"),
    SELECTED_LEVEL("<x> sentences referenced to selected level"),
    MOST_REPEATED("<x> sentences with the most repeated words"),
    LEAST_REPEATED("<x> sentences with the least repeated words"),
    BY_DATETIME_DESC("<x> sentences ordered by datetime from the newest"),
    BY_DATETIME_ASC("<x> sentences ordered by datetime from the oldest"),
    FAVORITE("<x> sentences from 'my favorite'"),
    BACK("back");

    private String option;

    LearnOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    @Override
    public String toString() {
        return String.format("%d. %s", ordinal() + 1, option);
    }

    public static void print() {
        for (LearnOptions o : LearnOptions.values()) {
            System.out.println(o);
        }
    }
}
