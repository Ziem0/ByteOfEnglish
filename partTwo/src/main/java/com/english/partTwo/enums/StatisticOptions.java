package com.english.partTwo.enums;

public enum StatisticOptions{ //} implements IMenuPrintable{

    STATUS_STATISTIC("Show status statistic"),
    BY_LETTER("Show all words starting with letter(s)"),
    MOST_REPEATED("Show the most repeated words"),
    LEAST_REPEATED("Show the least repeated words"),
    EXIT("Quit");

    private String option;
    private static int counter;

    StatisticOptions(String option) {
        this.option = option;
    }

    public static int getCounter() {
        return counter;
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

//    @Override
//    public int printMenu(String s) {
//        if (s.equals("up")) {
//            if (counter == 0) {
//                counter = StatisticOptions.values().length - 1;
//            } else {
//                counter--;
//            }
//        } else if (s.equals("down")) {
//            if (counter == StatisticOptions.values().length - 1) {
//                counter = 0;
//            } else {
//                counter++;
//            }
//        }
//        System.out.print("\033[H\033[2J"); System.out.flush();
//        for (StatisticOptions option : StatisticOptions.values()) {
//            if (option.ordinal() == counter) {
//                System.out.println(Colors.GREEN.getBg(String.valueOf(option)));
//            } else {
//                System.out.println(option);
//            }
//        }
//        return counter;
//    }

}
