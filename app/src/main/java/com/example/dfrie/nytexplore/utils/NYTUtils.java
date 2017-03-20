package com.example.dfrie.nytexplore.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dfrie on 3/19/2017.
 */

public class NYTUtils {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");

    public static final SimpleDateFormat NYT_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static Date parseDate(String s) {
        return SDF.parse(s, new ParsePosition(0));
    }

    public static String formatDate(Date date) {
        return SDF.format(date);
    }

    public static String formatNYTDate(String s) {
        Date date = parseDate(s);
        return NYT_FORMAT.format(date);
    }

}
