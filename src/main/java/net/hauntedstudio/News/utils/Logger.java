package net.hauntedstudio.News.utils;

public class Logger {

    private static final boolean debug = true;

    public static void info(String message) {
        System.out.println("INFO: " + message);
    }

    public static void warning(String message) {
        System.out.println("WARNING: " + message);
    }

    public static void error(String message) {
        System.err.println("ERROR: " + message);
    }

    public static void debug(String message) {
        if (debug) {
            System.out.println("DEBUG: " + message);
        }
    }
}