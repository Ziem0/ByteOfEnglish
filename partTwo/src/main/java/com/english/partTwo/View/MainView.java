package com.english.partTwo.View;

import com.english.partTwo.matcher.Matcher;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {

    public static int getUserNum(int lengthMenu) throws InputMismatchException{
        Scanner sc = new Scanner(System.in);
        int userNum = 0;
        try {
            userNum = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Only numbers allowed! Try again");
        }
        if (userNum < 1 || userNum > lengthMenu) {
            System.out.println("Invalid number! Choose number from 1 to " + lengthMenu + ". Try again");
            return getUserNum(lengthMenu);
        } else {
            return userNum;
        }
    }

    public static String getUserString() {
        Scanner sc = new Scanner(System.in);
        String userStr = sc.nextLine();
        if (Matcher.stringMatcher(userStr)) {
            System.out.println("Invalid text! Only letter(s) allowed. Try again");
            return getUserString();
        } else {
            return userStr;
        }
    }



    public static void showMessage(String s) {
        System.out.println(s);
    }

}
