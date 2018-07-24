package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Adjective extends AbstractModel {
    public Adjective(int id, String eng, String pl, int used, LocalDate localDate, LocalTime localTime, int favorite) {
        super(id, eng, pl, used, localDate, localTime, favorite);
    }


}
