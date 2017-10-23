package com.masbie.travelohealth.custom.gson.deserialization;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 3:39 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaLocalTimeDeserialization implements JsonDeserializer<LocalTime>
{
    @Override public LocalTime deserialize(JsonElement src, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        final @NonNull DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss");
        return format.parseLocalTime(src.getAsJsonPrimitive().getAsString());
    }
}
