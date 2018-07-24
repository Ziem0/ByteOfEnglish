package com.english.partThree.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Grama extends AbstractGramaModel {
    public Grama(int id, String name, int used, LocalDate localDate, LocalTime localTime) {
        super(id, name, used, localDate, localTime);
    }
}
