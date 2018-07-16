package com.english.partTwo.enums;

public enum Colors {
    BLACK(30, 40),
    RED(31, 41),
    GREEN(32, 42),
    YELLOW(33, 43),
    BLUE(34, 44),
    MAGENTA(35, 45),
    CYAN(36, 46),
    WHITE(37, 47),
    DEFAULT(39,49);

    private int fg;
    private int bg;

    Colors(int fg, int bg) {
        this.fg = fg;
        this.bg = bg;
    }

    public String getFg(String s) {
        return (char) 27 + "["+this.fg+"m"+s+ (char)27 + "[0m";
    }

    public String getBg(String s) {
        return (char) 27 + "["+this.bg+"m"+s+ (char)27 + "[0m";
    }

//    public static void main(String[] args) {
//        System.out.println(CYAN.getBg("Ziemo!"));
//    }
}
