//package com.english.partTwo.models;
//
//import com.english.partTwo.enums.StatisticOptions;
//import com.english.partTwo.exceptions.KeyListenerExe;
//
//import java.util.Scanner;
//
//public class Demo {
//
//    public void display1() {
//        Scanner sc = new Scanner(System.in);
//        boolean run = true;
//
//        while (run) {
//            System.out.println("choose option for display1");
//            String out = sc.next();
//            switch (out) {
//                case "1":
//                    System.out.println("one");
//                    break;
//                case "2":
//                    System.out.println("bye");
//                    run = false;
//                    break;
//                case "3":
//                    System.out.println("start display1 again");
//                    display1();
//                    break;
//            }
//        }
//    }
//
//    public void display2() {
//        Scanner sc = new Scanner(System.in);
//        boolean run = true;
//
//        while (run) {
//            System.out.println("choose option for display2");
//            String out = sc.next();
//            switch (out) {
//                case "1":
//                    System.out.println("jeden");
//                    break;
//                case "2":
//                    System.out.println("dwa");
//                    run = false;
//                    break;
//                case "3":
//                    System.out.println("start display2 again");
//                    display2();
//                    break;
//            }
//        }
//    }
//
//    public void listener() {
//        KeyListenerExe exe = new KeyListenerExe(StatisticOptions.EXIT);
//    }
//
//    public static void main(String[] args) {
//        Demo demo = new Demo();
//        Thread t1 = new Thread(demo::display1);
//        Thread t2 = new Thread(demo::display2);
//        Thread t3 = new Thread(demo::listener);
//
//        t3.start();
//        t1.start();
//
//    }
//}
