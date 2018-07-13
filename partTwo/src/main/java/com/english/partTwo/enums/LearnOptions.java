package com.english.partTwo.enums;

public enum LearnOptions {

    GENERAL("<x> general random words"),
    MIXED_STATUS("<x> random words from each status"),
    FROM_DAYS("<x> last day(s) words"),
    LAST_WORDS("<x> last repeated words"),
    UNKNOWN("<x> unknown words"),
    MIDDLE_KNOWN("<x> middle known words"),
    KNOWN("<x> known words"),
    EXIT("Quit");

    private String option;

    LearnOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.ordinal()+1, this.option);
    }

    public static void printMenu() {
        for (LearnOptions option : LearnOptions.values()) {
            System.out.println(option);
        }
    }
}
