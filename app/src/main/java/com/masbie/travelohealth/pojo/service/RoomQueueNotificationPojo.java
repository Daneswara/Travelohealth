package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 07 September 2017, 8:56 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalDateDeserialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalDateSerialization;
import org.joda.time.LocalDate;

public class RoomQueueNotificationPojo
{
    @SerializedName("processed")
    @Nullable Integer   processed;
    @SerializedName("order")
    @Nullable LocalDate order;
    @SerializedName("type")
    @Nullable String    type;

    public RoomQueueNotificationPojo()
    {
    }

    public RoomQueueNotificationPojo(@Nullable Integer processed, @Nullable LocalDate order, @Nullable String type)
    {
        this.processed = processed;
        this.order = order;
        this.type = type;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateSerialization());
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateDeserialization());
        return builder;
    }

    @Nullable public Integer getProcessed()
    {
        return this.processed;
    }

    public void setProcessed(@Nullable Integer processed)
    {
        this.processed = processed;
    }

    @Nullable public LocalDate getOrder()
    {
        return this.order;
    }

    public void setOrder(@Nullable LocalDate order)
    {
        this.order = order;
    }

    @Nullable public String getType()
    {
        return this.type;
    }

    public void setType(@Nullable String type)
    {
        this.type = type;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomQueueNotificationPojo))
        {
            return false;
        }

        RoomQueueNotificationPojo that = (RoomQueueNotificationPojo) o;

        if(getProcessed() != null ? !getProcessed().equals(that.getProcessed()) : that.getProcessed() != null)
        {
            return false;
        }
        if(getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null)
        {
            return false;
        }
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;

    }

    @Override public int hashCode()
    {
        int result = getProcessed() != null ? getProcessed().hashCode() : 0;
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomQueueNotificationOrm{" + "processed=" + processed +
                ", order=" + order +
                ", type='" + type + '\'' +
                '}';
    }
}
