package com.niuchaoqun.springboot.security.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author niuchaoqun
 */
public class CamelCaseUtil {
    private static final char SEPARATOR = '_';

    private static final Pattern UPPER_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线
     *
     * @param input
     * @return
     */
    public static String camelToUnder(String input) {
        String str = input;
        if (Character.isUpperCase(input.charAt(0))) {
            char[] chars = str.toCharArray();
            chars[0] += 32;
            str = String.valueOf(chars);
        }

        Matcher matcher = UPPER_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param input
     * @return
     */
    public static String underToCamel(String input) {
        if (input == null) {
            return null;
        }
        input = input.toLowerCase();
        int length = input.length();

        StringBuilder sb = new StringBuilder(length);
        boolean upperCase = false;
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
