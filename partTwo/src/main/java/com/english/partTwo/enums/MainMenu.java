package com.english.partTwo.enums;

public enum MainMenu{

    LEARN_MENU,
    STATISTIC_MENU,
    EXIT_PROGRAM;

    MainMenu() {
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.ordinal()+1, super.toString());
    }

    public static void printMenu() {
        for (MainMenu option : MainMenu.values()) {
            System.out.println(option);
        }
    }
}
