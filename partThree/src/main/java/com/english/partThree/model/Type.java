package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Type extends AbstractGramaModel{
    public Type(int id, String name, int used, LocalDate localDate, LocalTime localTime) {
        super(id, name, used, localDate, localTime);
    }


}
