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
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class RoomQueueProcessedPojo extends RoomQueuePojo
{
    @SerializedName("room_summary")
    @Nullable private RoomSummaryPojo roomSummary;
    @Expose
    @Nullable private Integer         queueProcessed;

    public RoomQueueProcessedPojo()
    {
    }

    public RoomQueueProcessedPojo(@Nullable Integer id, @Nullable Integer queue, @Nullable Integer userGroup, @Nullable Integer roomClassSector, @Nullable LocalDate order, @Nullable String validity, @Nullable Integer processed, @Nullable String message, @Nullable Integer roomSummary, @Nullable LocalDateTime timestamp)
    {
        super(id, queue, userGroup, roomClassSector, order, validity, processed, message, roomSummary, timestamp);
    }

    public RoomQueueProcessedPojo(@Nullable Integer id, @Nullable Integer queue, @Nullable Integer userGroup, @Nullable Integer roomClassSector, @Nullable LocalDate order, @Nullable String validity, @Nullable Integer processed, @Nullable String message, @Nullable Integer room, @Nullable LocalDateTime timestamp, @Nullable RoomSummaryPojo roomSummary, @Nullable Integer queueProcessed)
    {
        super(id, queue, userGroup, roomClassSector, order, validity, processed, message, room, timestamp);
        this.roomSummary = roomSummary;
        this.queueProcessed = queueProcessed;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        RoomSummaryPojo.inferenceGsonBuilder(builder);
        RoomQueuePojo.inferenceGsonBuilder(builder);
    }

    public static RoomQueueProcessedPojo fromJson(String result)
    {
        GsonBuilder builder = new GsonBuilder();
        RoomQueueProcessedPojo.inferenceGsonBuilder(builder);
        Gson gson = builder.create();
        return gson.fromJson(result, RoomQueueProcessedPojo.class);
    }

    @Nullable public RoomSummaryPojo getRoomSummary()
    {
        return this.roomSummary;
    }

    public void setRoomSummary(@Nullable RoomSummaryPojo room)
    {
        this.roomSummary = room;
    }

    @Nullable public Integer getQueueProcessed()
    {
        return this.queueProcessed;
    }

    public void setQueueProcessed(@Nullable Integer queueProcessed)
    {
        this.queueProcessed = queueProcessed;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomQueueProcessedPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        RoomQueueProcessedPojo that = (RoomQueueProcessedPojo) o;

        if(getRoomSummary() != null ? !getRoomSummary().equals(that.getRoomSummary()) : that.getRoomSummary() != null)
        {
            return false;
        }
        return getQueueProcessed() != null ? getQueueProcessed().equals(that.getQueueProcessed()) : that.getQueueProcessed() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getRoomSummary() != null ? getRoomSummary().hashCode() : 0);
        result = 31 * result + (getQueueProcessed() != null ? getQueueProcessed().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomQueueProcessedPojo{" + "id=" + id +
                ", queue=" + queue +
                ", userGroup=" + userGroup +
                ", roomClassSector=" + roomClassSector +
                ", order=" + order +
                ", validity='" + validity + '\'' +
                ", queueProcessed=" + processed +
                ", message='" + message + '\'' +
                ", room=" + room +
                ", timestamp=" + timestamp +
                ", roomSummary=" + roomSummary +
                ", queueProcessed=" + queueProcessed +
                '}';
    }
}
