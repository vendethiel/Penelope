package org.penelope.utils;

public class StringUtils {
    static public String ucFirst(String str) {
        return str.substring(0, 1).toUpperCase()
                + str.substring(1).toLowerCase();
    }
}
