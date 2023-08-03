package com.dev.was.util;

import java.time.format.DateTimeFormatter;

public class CommonUtil {
    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String toNotNullString(String s) {
        return (s == null) ? "" : s;
    }

    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER2 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter LOCAL_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HHmmss");
}
