package com.masbie.travelohealth.custom.gson.deserialization;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 3:39 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaLocalDateTimeDeserialization implements JsonDeserializer<LocalDateTime>
{
    @Override public LocalDateTime deserialize(JsonElement src, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        final @NotNull DateTimeFormatter format = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
        return format.parseLocalDateTime(src.getAsJsonPrimitive().getAsString());
    }
}
