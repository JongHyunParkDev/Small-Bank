package com.dev.was.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DBUtil {
    private DBUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static LocalDateTime toLocalDateTime(Object obj)
    {
        if (obj == null)
            return null;

        if (obj instanceof String){ // yyyyMMddHHmmss String
            return LocalDateTime.parse((String)obj, CommonUtil.LOCAL_DATE_TIME_FORMATTER);
        }
        else if (obj instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp)obj).toLocalDateTime();
        }
        else
            // TODO 변경해야한다. API Exception 를 새로 만들자.
            throw new RuntimeException("convert fail");
    }

    public static LocalDate toLocalDate(Object obj)
    {
        if (obj == null)
            return null;

        if (obj instanceof String) { // YYYYMMDD String
            return LocalDate.parse((String)obj, CommonUtil.LOCAL_DATE_FORMATTER);
        }
        else if (obj instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp)obj).toLocalDateTime().toLocalDate();
        }
        else
            // TODO 변경해야한다. API Exception 를 새로 만들자.
            throw new RuntimeException("convert fail");
    }

    public static LocalTime toLocalTime(Object obj) {
        if (obj == null)
            return null;

        if (obj instanceof String) { // HHmmss String
            return LocalTime.parse((String)obj, CommonUtil.LOCAL_TIME_FORMATTER);
        }
        else if (obj instanceof java.sql.Timestamp){
            return ((java.sql.Timestamp)obj).toLocalDateTime().toLocalTime();
        }
        else
            // TODO 변경해야한다. API Exception 를 새로 만들자.
            throw new RuntimeException("convert fail");
    }
}
