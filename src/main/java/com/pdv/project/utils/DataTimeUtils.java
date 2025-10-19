package com.pdv.project.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DataTimeUtils {

    // ---- Date and time format patterns
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId ZONE_ARGENTINA = ZoneId.of("America/Argentina/Buenos_Aires");

    /**
     * The dates requested for reservations must be after 
     * today's date. Both the start and end dates must be entered 
     * using this method to validate that the request is not invalid.
     */
    public static boolean validateDate(String dateTime) {

        LocalDateTime input = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        ZonedDateTime inputZoned = input.atZone(ZONE_ARGENTINA);
        ZonedDateTime nowArgentina = ZonedDateTime.now(ZONE_ARGENTINA);
        return inputZoned.isAfter(nowArgentina);

    }

}