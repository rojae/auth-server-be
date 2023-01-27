package io.github.rojae.authcoreapi.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String toString(LocalDateTime localDateTime){
        if(localDateTime == null)
            return "";
        else
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
