package com.dev.was.util;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class CommonUtil {

    public static String toNotNullString(String s) {
        return (s == null) ? "" : s;
    }

    public static String getExceptionReasons(Throwable e) {
        if (e == null)
            return "";

        StringBuilder sb = new StringBuilder();
        Throwable cause = null;
        Throwable result = e;

        sb.append(e.getMessage());
        int cnt = 0;
        while(null != (cause = result.getCause()) && (result != cause)) {
            sb.append(" (").append(cause.getMessage());
            result = cause;
            cnt++;
        }

        for (int i = 0; i < cnt; i++) {
            sb.append(")");
        }

        return sb.toString();
    }

    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER2 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter LOCAL_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final DateTimeFormatter LOCAL_TIME_FORMATTER2 =
            DateTimeFormatter.ofPattern("HHmm");
    public static final DateTimeFormatter LOCAL_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HHmmss");
}
