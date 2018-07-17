package com.english.client.enums;

public enum Menu {

    GRAMA,
    WORDS_3000,
    CLOSE_PROGRAM;

    @Override
    public String toString() {
        return String.format("%d:%s", this.ordinal()+1, this.name());
    }

    public static void print() {
        for (Menu m : Menu.values()) {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {
        print();
    }
}
