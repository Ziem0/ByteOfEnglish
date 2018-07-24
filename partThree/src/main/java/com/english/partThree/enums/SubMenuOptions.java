package com.english.partThree.enums;

public enum SubMenuOptions {

    CONTINUE("\n\nPress ENTER to continue"),
    EDIT("(E)dit selected part"),
    FAVORITE("(A)dd selected part to 'favorite'");

    private String option;

    SubMenuOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    @Override
    public String toString() {
        return String.format("%s", option);
    }

    public static void print() {
        for (SubMenuOptions o : SubMenuOptions.values()) {
            System.out.println(o);
        }
    }
}
