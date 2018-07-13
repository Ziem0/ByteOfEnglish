package com.english.partTwo.enums;

public enum UpdateOptions {

    NEXT("Just ENTER"),
    INCREASE_STATUS("Type: 1"),
    DECREASE_STATUS("Type: 2"),
    ADD_TO_TRASH("Type: 3"),
    EDIT_WORD("Type 4");

    private String description;

    UpdateOptions(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%d: %-5s %s", this.ordinal() + 1, this.name(), this.description);
    }

    public static void printMenu() {
        for (UpdateOptions option : UpdateOptions.values()) {
            System.out.println(option);
        }
    }
}
