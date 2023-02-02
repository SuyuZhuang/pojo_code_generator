package com.asu.plugins.pojo_code_generator.utils;

public class StrUtils {


    /**
     * 可能是`id`
     */
    public static String parseName(String name, char token) {
        name = name.trim();
        if (name.indexOf(token) <0) {
            return name;
        }
        return name.substring(1, name.length() - 1);
    }

    public static String toCamelCase(CharSequence name) {
        char symbol = '_';
        if (name == null) {
            return null;
        }
        String nameStr = name.toString();
        if (nameStr.indexOf(symbol) == -1) {
            return nameStr;
        }

        int length = nameStr.length();
        StringBuilder sb = new StringBuilder(length);
        boolean upperCase = false;

        for (int i = 0; i < length; i++) {
            char c = nameStr.charAt(i);
            if (c == symbol) {
                upperCase = true;
                continue;
            }
            if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }

        return sb.toString();
    }
}
