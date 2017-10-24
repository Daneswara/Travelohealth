package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 06 September 2017, 5:37 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class RoomQueueSummaryPojo extends RoomQueuePojo
{
    @SerializedName("room_summary")
    @Nullable private RoomSummaryPojo roomSummary;

    public RoomQueueSummaryPojo()
    {
    }

    public RoomQueueSummaryPojo(@Nullable Integer id, @Nullable Integer queue, @Nullable Integer userGroup, @Nullable Integer roomClassSector, @Nullable LocalDate order, @Nullable String validity, @Nullable Integer processed, @Nullable String message, @Nullable Integer roomSummary, @Nullable LocalDateTime timestamp)
    {
        super(id, queue, userGroup, roomClassSector, order, validity, processed, message, roomSummary, timestamp);
    }

    public RoomQueueSummaryPojo(@Nullable Integer id, @Nullable Integer queue, @Nullable Integer userGroup, @Nullable Integer roomClassSector, @Nullable LocalDate order, @Nullable String validity, @Nullable Integer processed, @Nullable String message, @Nullable Integer room, @Nullable LocalDateTime timestamp, @Nullable RoomSummaryPojo roomSummary)
    {
        super(id, queue, userGroup, roomClassSector, order, validity, processed, message, room, timestamp);
        this.roomSummary = roomSummary;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        RoomSummaryPojo.inferenceGsonBuilder(builder);
        RoomQueuePojo.inferenceGsonBuilder(builder);
    }

    public static RoomQueueSummaryPojo fromJson(String result)
    {
        GsonBuilder builder = new GsonBuilder();
        RoomQueueSummaryPojo.inferenceGsonBuilder(builder);
        Gson gson = builder.create();
        return gson.fromJson(result, RoomQueueSummaryPojo.class);
    }

    @Nullable public RoomSummaryPojo getRoomSummary()
    {
        return this.roomSummary;
    }

    public void setRoomSummary(@Nullable RoomSummaryPojo room)
    {
        this.roomSummary = room;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomQueueSummaryPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        RoomQueueSummaryPojo that = (RoomQueueSummaryPojo) o;

        return getRoom() != null ? getRoom().equals(that.getRoom()) : that.getRoom() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomQueueSummaryPojo{" + "id=" + id +
                ", queue=" + queue +
                ", userGroup=" + userGroup +
                ", roomClassSector=" + roomClassSector +
                ", order=" + order +
                ", validity='" + validity + '\'' +
                ", processed=" + processed +
                ", message='" + message + '\'' +
                ", room=" + room +
                ", timestamp=" + timestamp +
                ", roomSummary=" + roomSummary +
                '}';
    }
}
