package com.dev.was.util;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;

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
            throw new ApiException(ExceptionCodeEnum.INVALID_ARGUMENT, "Failed Invalid Argument");
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
            throw new ApiException(ExceptionCodeEnum.INVALID_ARGUMENT, "Failed Invalid Argument");
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
            throw new ApiException(ExceptionCodeEnum.INVALID_ARGUMENT, "Failed Invalid Argument");
    }
}
