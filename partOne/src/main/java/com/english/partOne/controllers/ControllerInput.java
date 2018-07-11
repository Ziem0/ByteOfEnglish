package com.english.partOne.controllers;

public class ControllerInput {
    public static boolean InvalidNumberInput(String number) {
        return number.matches("[1-2]{1}");
    }
}
