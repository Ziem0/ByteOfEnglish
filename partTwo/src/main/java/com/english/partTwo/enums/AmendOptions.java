package com.english.partTwo.enums;

public enum AmendOptions {
    ADD_WORD("Add new word"),
    DELETE_WORD("Delete selected word"),
    BACK("Back");

    private String option;

    AmendOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.ordinal() + 1, this.getOption());
    }

    public static void print() {
        for (AmendOptions i : AmendOptions.values()) {
            System.out.println(i);
        }
    }
}
