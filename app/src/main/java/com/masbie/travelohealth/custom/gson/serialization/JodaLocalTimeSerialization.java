package com.masbie.travelohealth.custom.gson.serialization;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 3:32 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaLocalTimeSerialization implements JsonSerializer<LocalTime>
{
    @Override public JsonElement serialize(LocalTime src, Type type, JsonSerializationContext jsonSerializationContext)
    {
        final @NotNull DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss");
        return new JsonPrimitive(format.print(src));
    }
}
