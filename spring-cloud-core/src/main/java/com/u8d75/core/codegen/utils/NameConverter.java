package com.u8d75.core.codegen.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * NameConverter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NameConverter {

    /**
     * toJavaCase
     *
     * @param s s
     * @return String
     */
    public static String toJavaCase(String s) {
        if ((s == null) || (s.trim().length() == 0)) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        String[] array = s.split("_");
        boolean firstTime = true;
        for (String e : array) {
            if (e.length() == 0) {
                continue;
            }
            if (e.length() == 1) {
                sb.append((!(firstTime)) ? e.toUpperCase() : e);
            } else {
                sb.append((!(firstTime)) ? e.substring(0, 1).toUpperCase() + e.substring(1) : e);
            }
            firstTime = false;
        }
        return sb.toString();
    }

    /**
     * toDbCase
     *
     * @param s s
     * @return String
     */
    public static String toDbCase(String s) {
        if ((s == null) || (s.trim().length() == 0)) {
            return s;
        }
        char[] chars = s.toCharArray();

        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if ((c >= 'A') && (c <= 'Z')) {
                char c1 = (char) (c + ' ');
                sb.append("_" + c1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}