package com.english.partTwo.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Word {

    private int id;
    private String eng;
    private String pl;
    private String status;
    private int statusID;
    private LocalDate date;
    private LocalTime hour;
    private int repeated;

    public Word(int id, String eng, String pl, String status, int statusID, LocalDate date, LocalTime hour, int repeated) {
        this.id = id;
        this.eng = eng;
        this.pl = pl;
        this.status = status;
        this.statusID = statusID;
        this.date = date;
        this.hour = hour;
        this.repeated = repeated;
    }

    public Word(String eng, String pl) {
        this.eng = eng;
        this.pl = pl;
    }

    public int getId() {
        return id;
    }

    public String getEng() {
        return eng;
    }

    public String getPl() {
        return pl;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusID() {
        return statusID;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public int getRepeated() {
        return repeated;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public void increaseStatus() {
        if (statusID == 3) {
            System.out.printf("\nIncrease is not allowed. Your current status is %s\n\n", status);
        } else {
            this.statusID++;
            System.out.printf("\nDone! Your current status is level up from %s\n\n", status);
        }
    }

    public void decreaseStatus() {
        if (statusID == 1) {
            System.out.printf("Decrease is not allowed. Your current status is %s\n\n", status);
        } else {
            this.statusID--;
            System.out.printf("Done! Your current status is level down from %s\n\n", status);
        }
    }

    public void updateDate() {
        this.date = LocalDate.now();
    }

    public void updateHour() {
        this.hour = LocalTime.now();
    }

    public void updateRepeated() {
        this.repeated++;
    }

    public void setTrashStatus() {
        this.statusID = 4;
        System.out.printf("Done! This word has been moved to trash..\n\n");
    }

    @Override
    public String toString() {
        return String.format("eng:%-15s pl:%-15s repeated:%-15s date:%-15s status:%-15s\n", eng, pl, repeated, date, status);
    }

}
