package org.example.utils;

public class Utils {

    public static Boolean isBlank(String str) {
        return str == null || str.trim().isBlank();
    }
}
