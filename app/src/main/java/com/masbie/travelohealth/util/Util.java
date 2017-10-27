package com.masbie.travelohealth.util;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 27 October 2017, 7:55 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Util
{
    public static DateTimeFormatter formatLocalDate     = DateTimeFormat.forPattern("YYYY-MM-dd");
    public static DateTimeFormatter formatLocalDateTime = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
    public static DateTimeFormatter formatLocalTime     = DateTimeFormat.forPattern("HH:mm:ss");
}
