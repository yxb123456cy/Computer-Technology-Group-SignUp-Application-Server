package com.lemon.computer.utils;

import java.util.*;

/**
 * 自定义字符串工具类，提供丰富、安全、高效的字符串操作方法。
 * <p>
 * 所有方法都对 {@code null} 输入进行了友好处理，不会抛出 {@link NullPointerException}。
 * </p>
 *
 * @author Generated
 * @version 1.0
 */
public final class CustomStringUtils {

    private CustomStringUtils() {
        // 工具类，禁止实例化
    }

    // ==================== 常量定义 ====================
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final int INDEX_NOT_FOUND = -1;

    // ==================== 空值与空白检查 ====================

    /**
     * 检查字符串是否为 {@code null} 或长度为 0 ("")。
     *
     * @param str 要检查的字符串
     * @return 如果为 {@code null} 或 ""，则返回 {@code true}
     */
    public static boolean isEmpty(final CharSequence str) {
        return str == null || str.isEmpty();
    }

    /**
     * 检查字符串是否不为 {@code null} 且长度不为 0。
     *
     * @param str 要检查的字符串
     * @return 如果不为 {@code null} 且不为 ""，则返回 {@code true}
     */
    public static boolean isNotEmpty(final CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 检查字符串是否为 {@code null}、空字符串 ("") 或仅包含空白字符。
     *
     * @param str 要检查的字符串
     * @return 如果为 {@code null}、"" 或只包含空白字符，则返回 {@code true}
     */
    public static boolean isBlank(final CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否不为 {@code null}、不为空且不只包含空白字符。
     *
     * @param str 要检查的字符串
     * @return 如果不为 {@code null}、不为空且不只包含空白字符，则返回 {@code true}
     */
    public static boolean isNotBlank(final CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 如果字符串为 {@code null} 或空，则返回默认值。
     *
     * @param str          要检查的字符串
     * @param defaultValue 默认值
     * @return 原字符串或默认值
     */
    public static String defaultIfEmpty(final String str, final String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * 如果字符串为 {@code null}、空或只包含空白字符，则返回默认值。
     *
     * @param str          要检查的字符串
     * @param defaultValue 默认值
     * @return 原字符串或默认值
     */
    public static String defaultIfBlank(final String str, final String defaultValue) {
        return isBlank(str) ? defaultValue : str;
    }

    // ==================== 截取、填充与对齐 ====================

    /**
     * 截断字符串到指定长度。
     *
     * @param str   原字符串
     * @param maxWidth 最大宽度
     * @return 截断后的字符串
     */
    public static String truncate(final String str, final int maxWidth) {
        return truncate(str, maxWidth, "...");
    }

    /**
     * 截断字符串到指定长度，并添加省略号。
     *
     * @param str   原字符串
     * @param maxWidth 最大宽度
     * @param omission 省略符号
     * @return 截断后的字符串
     */
    public static String truncate(final String str, final int maxWidth, final String omission) {
        if (isBlank(str)) {
            return str;
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWidth cannot be negative");
        }
        if (str.length() <= maxWidth) {
            return str;
        }
        if (maxWidth == 0) {
            return EMPTY;
        }
        int omissionLength = omission == null ? 0 : omission.length();
        if (omissionLength >= maxWidth) {
            return omission.substring(0, maxWidth);
        }
        return str.substring(0, maxWidth - omissionLength) + omission;
    }

    /**
     * 左填充字符串。
     *
     * @param str    原字符串
     * @param size   填充后的总长度
     * @param padChar 填充字符
     * @return 填充后的字符串
     */
    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int padLength = size - str.length();
        if (padLength <= 0) {
            return str;
        }
        return repeat(padChar, padLength).concat(str);
    }

    /**
     * 右填充字符串。
     *
     * @param str    原字符串
     * @param size   填充后的总长度
     * @param padChar 填充字符
     * @return 填充后的字符串
     */
    public static String rightPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int padLength = size - str.length();
        if (padLength <= 0) {
            return str;
        }
        return str.concat(repeat(padChar, padLength));
    }

    // ==================== 格式化与转换 ====================

    /**
     * 首字母大写。
     *
     * @param str 原字符串
     * @return 首字母大写的字符串
     */
    public static String capitalize(final String str) {
        if (isBlank(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    /**
     * 驼峰命名法转下划线命名法 (e.g., "helloWorld" -> "hello_world")。
     *
     * @param str 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String toSnakeCase(final String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * 下划线/短横线命名法转驼峰命名法 (e.g., "hello_world" -> "helloWorld")。
     *
     * @param str 下划线或短横线命名字符串
     * @return 驼峰命名字符串
     */
    public static String toCamelCase(final String str) {
        if (isBlank(str)) {
            return str;
        }
        String[] parts = str.split("[-_]");
        StringBuilder sb = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                sb.append(Character.toUpperCase(parts[i].charAt(0)));
                sb.append(parts[i].substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 反转字符串。
     *
     * @param str 原字符串
     * @return 反转后的字符串
     */
    public static String reverse(final String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 用指定字符包裹字符串。
     *
     * @param str    原字符串
     * @param wrapWith 包裹字符
     * @return 包裹后的字符串
     */
    public static String wrap(final String str, final char wrapWith) {
        if (isEmpty(str)) {
            return String.valueOf(wrapWith) + wrapWith;
        }
        return wrapWith + str + wrapWith;
    }

    // ==================== 内容检查与操作 ====================

    /**
     * 检查字符串是否只包含Unicode字母。
     *
     * @param str 要检查的字符串
     * @return 如果不为 {@code null} 且只包含字母，则返回 {@code true}
     */
    public static boolean isAlpha(final CharSequence str) {
        if (isEmpty(str)) {
            return false;
        }
        final int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否只包含数字。
     *
     * @param str 要检查的字符串
     * @return 如果不为 {@code null} 且只包含数字，则返回 {@code true}
     */
    public static boolean isNumeric(final CharSequence str) {
        if (isEmpty(str)) {
            return false;
        }
        final int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算子字符串在原字符串中出现的次数。
     *
     * @param str       原字符串
     * @param subStr    子字符串
     * @return 出现的次数
     */
    public static int countMatches(final CharSequence str, final CharSequence subStr) {
        if (isEmpty(str) || isEmpty(subStr)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = indexOf(str, subStr, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += subStr.length();
        }
        return count;
    }

    /**
     * 遮蔽字符串的一部分。
     *
     * @param str          原字符串
     * @param startMask    开始遮蔽的索引 (包含)
     * @param endMask      结束遮蔽的索引 (不包含)
     * @param maskChar     遮蔽字符
     * @return 遮蔽后的字符串
     */
    public static String mask(final String str, final int startMask, final int endMask, final char maskChar) {
        if (isEmpty(str)) {
            return str;
        }
        if (startMask < 0) {
            throw new StringIndexOutOfBoundsException("startMask cannot be negative");
        }
        if (endMask > str.length()) {
            throw new StringIndexOutOfBoundsException("endMask cannot be greater than the length of the string");
        }
        if (startMask > endMask) {
            throw new StringIndexOutOfBoundsException("startMask cannot be greater than endMask");
        }

        int maskLength = endMask - startMask;
        if (maskLength == 0) {
            return str;
        }

        String maskedPart = repeat(maskChar, maskLength);
        return str.substring(0, startMask) + maskedPart + str.substring(endMask);
    }

    // ==================== 集合与数组操作 ====================

    /**
     * 将集合元素连接成字符串。
     *
     * @param collection 集合
     * @param delimiter  分隔符
     * @param <T>        元素类型
     * @return 连接后的字符串
     */
    public static <T> String join(final Collection<T> collection, final String delimiter) {
        if (collection == null || collection.isEmpty()) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (T item : collection) {
            if (!first) {
                sb.append(delimiter);
            }
            sb.append(item);
            first = false;
        }
        return sb.toString();
    }

    /**
     * 将数组元素连接成字符串。
     *
     * @param array     数组
     * @param delimiter 分隔符
     * @return 连接后的字符串
     */
    public static String join(final Object[] array, final String delimiter) {
        if (array == null || array.length == 0) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(delimiter).append(array[i]);
        }
        return sb.toString();
    }

    // ==================== 高级功能 ====================

    /**
     * 计算两个字符串之间的 Levenshtein 距离（编辑距离）。
     * 距离越小，字符串越相似。
     *
     * @param s 第一个字符串
     * @param t 第二个字符串
     * @return 编辑距离
     */
    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= n; i++) {
            char s_i = s.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char t_j = t.charAt(j - 1);
                int cost = (s_i == t_j) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[n][m];
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 重复一个字符。
     */
    private static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return EMPTY;
        }
        char[] buf = new char[repeat];
        Arrays.fill(buf, ch);
        return new String(buf);
    }

    /**
     * 安全的 indexOf，处理 null 输入。
     */
    private static int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
        if (cs instanceof String) {
            return ((String) cs).indexOf(searchChar.toString(), start);
        } else if (cs instanceof StringBuilder) {
            return ((StringBuilder) cs).indexOf(searchChar.toString(), start);
        } else if (cs instanceof StringBuffer) {
            return ((StringBuffer) cs).indexOf(searchChar.toString(), start);
        }
        return cs.toString().indexOf(searchChar.toString(), start);
    }

    // ==================== 演示与测试 ====================

    public static void main(String[] args) {
        System.out.println("--- 空值与空白检查 ---");
        System.out.println("isBlank(null): " + isBlank(null));
        System.out.println("isBlank(\"\"): " + isBlank(""));
        System.out.println("isBlank(\" \"): " + isBlank(" "));
        System.out.println("defaultIfBlank(null, \"default\"): " + defaultIfBlank(null, "default"));

        System.out.println("\n--- 截取、填充与对齐 ---");
        System.out.println("truncate(\"HelloWorld\", 5): " + truncate("HelloWorld", 5));
        System.out.println("leftPad(\"1\", 5, '0'): " + leftPad("1", 5, '0'));

        System.out.println("\n--- 格式化与转换 ---");
        System.out.println("capitalize(\"HELLO\"): " + capitalize("HELLO"));
        System.out.println("toSnakeCase(\"helloWorldTest\"): " + toSnakeCase("helloWorldTest"));
        System.out.println("toCamelCase(\"hello_world_test\"): " + toCamelCase("hello_world_test"));
        System.out.println("reverse(\"abc\"): " + reverse("abc"));

        System.out.println("\n--- 内容检查与操作 ---");
        System.out.println("isAlpha(\"abc\"): " + isAlpha("abc"));
        System.out.println("isNumeric(\"123\"): " + isNumeric("123"));
        System.out.println("countMatches(\"docker\", \"ab\"): " + countMatches("docker", "ab"));
        System.out.println("mask(\"13812345678\", 3, 7, '*'): " + mask("13812345678", 3, 7, '*'));

        System.out.println("\n--- 集合与数组操作 ---");
        List<String> list = List.of("A", "B", "C");
        System.out.println("join(list, \", \"): " + join(list, ", "));
        System.out.println("join(new int[]{1,2,3}, \"-\"): " + join(new Object[]{1, 2, 3}, "-"));

        System.out.println("\n--- 高级功能 ---");
        System.out.println("Levenshtein distance(\"kitten\", \"sitting\"): " + getLevenshteinDistance("kitten", "sitting"));
    }
}
