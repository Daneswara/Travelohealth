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
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaLocalDateDeserialization implements JsonDeserializer<LocalDate>
{
    @Override public LocalDate deserialize(JsonElement src, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        final @NonNull DateTimeFormatter format = DateTimeFormat.forPattern("YYYY-MM-dd");
        return format.parseLocalDate(src.getAsJsonPrimitive().getAsString());
    }
}
