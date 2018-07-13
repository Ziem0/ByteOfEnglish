package com.english.partTwo.enums;

public enum TranslateOptions {

    ENG_PL,
    PL_ENG;

    @Override
    public String toString() {
        return String.format("%d: %s", this.ordinal()+1, this.name());
    }

    public static void printMenu() {
        for (TranslateOptions option : TranslateOptions.values()) {
            System.out.println(option);
        }
    }
}
