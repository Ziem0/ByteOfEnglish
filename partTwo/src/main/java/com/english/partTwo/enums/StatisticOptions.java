package com.english.partTwo.enums;

public enum StatisticOptions {

    STATUS_STATISTIC("Show status statistic"),
    BY_LETTER("Show all words starting with letter(s)"),
    MOST_REPEATED("Show the most repeated words"),
    LEAST_REPEATED("Show the least repeated words"),
    EXIT("Quit");

    private String option;

    StatisticOptions(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.ordinal()+1, this.option);
    }

    public static void printMenu() {
        for (StatisticOptions option : StatisticOptions.values()) {
            System.out.println(option);
        }
    }
}
