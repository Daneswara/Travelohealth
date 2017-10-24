package com.masbie.travelohealth.custom.gson.serialization;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 3:32 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaLocalDateTimeSerialization implements JsonSerializer<LocalDateTime>
{
    @Override public JsonElement serialize(LocalDateTime src, Type type, JsonSerializationContext jsonSerializationContext)
    {
        final @NonNull DateTimeFormatter format = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
        return new JsonPrimitive(format.print(src));
    }
}
