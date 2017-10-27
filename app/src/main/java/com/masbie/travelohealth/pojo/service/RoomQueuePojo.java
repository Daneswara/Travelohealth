package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 5:37 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalDateDeserialization;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalDateTimeDeserialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalDateSerialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalDateTimeSerialization;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class RoomQueuePojo
{
    @SerializedName("id")
    @Nullable Integer       id;
    @SerializedName("queue")
    @Nullable Integer       queue;
    @SerializedName("user_group")
    @Nullable Integer       userGroup;
    @SerializedName("r_c_s")
    @Nullable Integer       roomClassSector;
    @SerializedName("order")
    @Nullable LocalDate     order;
    @SerializedName("validity")
    @Nullable String        validity;
    @SerializedName("processed")
    @Nullable Integer       processed;
    @SerializedName("message")
    @Nullable String        message;
    @SerializedName("room")
    @Nullable Integer       room;
    @SerializedName("create_at")
    @Nullable LocalDateTime timestamp;

    public RoomQueuePojo()
    {
    }

    public RoomQueuePojo(@Nullable Integer id, @Nullable Integer queue, @Nullable Integer userGroup, @Nullable Integer roomClassSector, @Nullable LocalDate order, @Nullable String validity, @Nullable Integer processed, @Nullable String message, @Nullable Integer room, @Nullable LocalDateTime timestamp)
    {
        this.id = id;
        this.queue = queue;
        this.userGroup = userGroup;
        this.roomClassSector = roomClassSector;
        this.order = order;
        this.validity = validity;
        this.processed = processed;
        this.message = message;
        this.room = room;
        this.timestamp = timestamp;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateSerialization());
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateDeserialization());
        builder.registerTypeAdapter(LocalDateTime.class, new JodaLocalDateTimeSerialization());
        builder.registerTypeAdapter(LocalDateTime.class, new JodaLocalDateTimeDeserialization());
        return builder;
    }

    @Nullable public Integer getId()
    {
        return this.id;
    }

    public void setId(@Nullable Integer id)
    {
        this.id = id;
    }

    @Nullable public Integer getQueue()
    {
        return this.queue;
    }

    public void setQueue(@Nullable Integer queue)
    {
        this.queue = queue;
    }

    @Nullable public Integer getUserGroup()
    {
        return this.userGroup;
    }

    public void setUserGroup(@Nullable Integer userGroup)
    {
        this.userGroup = userGroup;
    }

    @Nullable public Integer getRoomClassSector()
    {
        return this.roomClassSector;
    }

    public void setRoomClassSector(@Nullable Integer roomClassSector)
    {
        this.roomClassSector = roomClassSector;
    }

    @Nullable public LocalDate getOrder()
    {
        return this.order;
    }

    public void setOrder(@Nullable LocalDate order)
    {
        this.order = order;
    }

    @Nullable public String getValidity()
    {
        return this.validity;
    }

    public void setValidity(@Nullable String validity)
    {
        this.validity = validity;
    }

    @Nullable public Integer getProcessed()
    {
        return this.processed;
    }

    public void setProcessed(@Nullable Integer processed)
    {
        this.processed = processed;
    }

    @Nullable public String getMessage()
    {
        return this.message;
    }

    public void setMessage(@Nullable String message)
    {
        this.message = message;
    }

    @Nullable public Integer getRoom()
    {
        return this.room;
    }

    public void setRoom(@Nullable Integer room)
    {
        this.room = room;
    }

    @Nullable public LocalDateTime getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(@Nullable LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomQueuePojo))
        {
            return false;
        }

        RoomQueuePojo that = (RoomQueuePojo) o;

        if(getId() != null ? !getId().equals(that.getId()) : that.getId() != null)
        {
            return false;
        }
        if(getQueue() != null ? !getQueue().equals(that.getQueue()) : that.getQueue() != null)
        {
            return false;
        }
        if(getUserGroup() != null ? !getUserGroup().equals(that.getUserGroup()) : that.getUserGroup() != null)
        {
            return false;
        }
        if(getRoomClassSector() != null ? !getRoomClassSector().equals(that.getRoomClassSector()) : that.getRoomClassSector() != null)
        {
            return false;
        }
        if(getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null)
        {
            return false;
        }
        if(getValidity() != null ? !getValidity().equals(that.getValidity()) : that.getValidity() != null)
        {
            return false;
        }
        if(getProcessed() != null ? !getProcessed().equals(that.getProcessed()) : that.getProcessed() != null)
        {
            return false;
        }
        if(getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null)
        {
            return false;
        }
        if(getRoom() != null ? !getRoom().equals(that.getRoom()) : that.getRoom() != null)
        {
            return false;
        }
        return getTimestamp() != null ? getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() == null;

    }

    @Override public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getQueue() != null ? getQueue().hashCode() : 0);
        result = 31 * result + (getUserGroup() != null ? getUserGroup().hashCode() : 0);
        result = 31 * result + (getRoomClassSector() != null ? getRoomClassSector().hashCode() : 0);
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getValidity() != null ? getValidity().hashCode() : 0);
        result = 31 * result + (getProcessed() != null ? getProcessed().hashCode() : 0);
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomQueuePojo{" + "id=" + id +
                ", queue=" + queue +
                ", userGroup=" + userGroup +
                ", roomClassSector=" + roomClassSector +
                ", order=" + order +
                ", validity='" + validity + '\'' +
                ", processed=" + processed +
                ", message='" + message + '\'' +
                ", room=" + room +
                ", timestamp=" + timestamp +
                '}';
    }
}
