package com.english.partTwo.enums;

public enum UpdateOptions {

    NEXT("press ENTER to continue"),
    INCREASE_STATUS("Type: '(U)pdate'"),
    DECREASE_STATUS("Type: '(D)ecrease'"),
    ADD_TO_TRASH("Type: '(T)rash'"),
    EDIT_WORD("Type '(E)dit'");

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
