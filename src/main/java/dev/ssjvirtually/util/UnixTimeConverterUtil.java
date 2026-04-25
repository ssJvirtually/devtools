package dev.ssjvirtually.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class UnixTimeConverterUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private UnixTimeConverterUtil() {
    }

    public static String unixToLocalDateTimeString(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static long localDateTimeToUnix(String localDateTimeText) {
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeText, DATE_TIME_FORMATTER);
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
