package com.ems.util;

public class Components {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause() {
        System.out.print("Press Enter to continue . . . ");
        new java.util.Scanner(System.in).nextLine();
    }
}
