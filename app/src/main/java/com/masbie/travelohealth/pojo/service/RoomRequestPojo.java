package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 5:24 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalDateDeserialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalDateSerialization;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;
import org.joda.time.LocalDate;

public class RoomRequestPojo
{
    @SerializedName("id")
    @Nullable Integer   roomSectorId;
    @SerializedName("date")
    @Nullable LocalDate date;

    public RoomRequestPojo()
    {
    }

    public RoomRequestPojo(@Nullable Integer roomSectorId, @Nullable LocalDate date)
    {
        this.roomSectorId = roomSectorId;
        this.date = date;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateSerialization());
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateDeserialization());
    }

    @Nullable public Integer getRoomSectorId()
    {
        return this.roomSectorId;
    }

    public void setRoomSectorId(@Nullable Integer roomSectorId)
    {
        this.roomSectorId = roomSectorId;
    }

    @Nullable public LocalDate getDate()
    {
        return this.date;
    }

    public void setDate(@Nullable LocalDate date)
    {
        this.date = date;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomRequestPojo))
        {
            return false;
        }

        RoomRequestPojo that = (RoomRequestPojo) o;

        if(getRoomSectorId() != null ? !getRoomSectorId().equals(that.getRoomSectorId()) : that.getRoomSectorId() != null)
        {
            return false;
        }
        return getDate() != null ? getDate().equals(that.getDate()) : that.getDate() == null;

    }

    @Override public int hashCode()
    {
        int result = getRoomSectorId() != null ? getRoomSectorId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        final StringBuilder sb = new StringBuilder("RoomRequestPojo{");
        sb.append("roomSectorId=").append(roomSectorId);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }

    public Map<String, RequestBody> partMap()
    {
        GsonBuilder builder = new GsonBuilder();
        RoomRequestPojo.inferenceGsonBuilder(builder);
        Gson        gson = builder.create();
        RequestBody id   = RequestBody.create(okhttp3.MultipartBody.FORM, gson.toJsonTree(this.getRoomSectorId()).getAsString());
        RequestBody date = RequestBody.create(okhttp3.MultipartBody.FORM, gson.toJsonTree(this.getDate()).getAsString());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("id", id);
        map.put("date", date);
        return map;
    }
}
