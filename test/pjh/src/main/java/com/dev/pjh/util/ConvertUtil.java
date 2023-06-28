package com.dev.pjh.util;

import java.nio.charset.StandardCharsets;

public class ConvertUtil {

    public static String toStrUTF8(String str) {
        return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
