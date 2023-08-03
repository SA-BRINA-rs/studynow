package com.sabrina.studynow.util;

import java.time.LocalDate;

public class SanitizerUtil {

    public static Boolean parseBoolean(String bool) {
        if (bool != null && !bool.equalsIgnoreCase("null")){
            return bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("on");
        }
        return false;
    }

    public static String parseString(String string) {
        return string != null && !string.equalsIgnoreCase("null") ? string : null;
    }

    public static LocalDate parseDate(String date) {
        return date != null && !date.equalsIgnoreCase("null") ? LocalDate.parse(date) : null;
    }

    public static Double parsePrice(String price) {
        return price != null && !price.equalsIgnoreCase("null") ? Double.parseDouble(price) : -1.00;
    }

    public static Long parseReferenceId(String referenceId) {
        return referenceId != null && !referenceId.equalsIgnoreCase("-1") ? Integer.parseInt(referenceId) : -1L;
    }
}
