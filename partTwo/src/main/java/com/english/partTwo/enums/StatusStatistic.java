package com.english.partTwo.enums;

import com.english.partTwo.dao.WordsDao;
import com.google.common.base.Strings;

public enum StatusStatistic {
    UNKNOWN(WordsDao.getDao().getLastID(),WordsDao.getDao().getProportion("unknown")),
    MIDDLE_KNOWN(WordsDao.getDao().getLastID(),WordsDao.getDao().getProportion("middleKnown")),
    KNOWN(WordsDao.getDao().getLastID(),WordsDao.getDao().getProportion("known"));

    private int sum;
    private int amount;

    StatusStatistic(int sum, int amount) {
        this.sum = sum;
        this.amount = amount;
    }

    private int getProportion() {
        return Math.round(((amount * 100) / sum)+1);
    }

    @Override
    public String toString() {
        if (this.name().equalsIgnoreCase(StatusStatistic.KNOWN.name())) {
            return Colors.GREEN.getBg(Strings.repeat(" ",this.getProportion()));
        } else if (this.name().equalsIgnoreCase(StatusStatistic.UNKNOWN.name())) {
            return Colors.RED.getBg(Strings.repeat(" ", this.getProportion()));
        } else {
            return Colors.BLUE.getBg(Strings.repeat(" ",this.getProportion()));
        }
    }

    public static void print() {
        for (StatusStatistic s : StatusStatistic.values()) {
            System.out.printf("%-10s %d'/,\t\t\t\t\t\t\t\t\t\t", s.name(), s.getProportion()-1);
        }
        System.out.println("\n");
        for (StatusStatistic s : StatusStatistic.values()) {
            System.out.printf("%s", s);
        }
    }


    public static void main(String[] args) {
        print();
    }


}
