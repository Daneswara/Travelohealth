package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 02 September 2017, 3:37 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDate;

public class ServiceQueueNotificationPojo extends RoomQueueNotificationPojo
{
    @SerializedName("service")
    @Nullable Integer service;

    public ServiceQueueNotificationPojo()
    {
    }

    public ServiceQueueNotificationPojo(@Nullable Integer service)
    {
        this.service = service;
    }

    public ServiceQueueNotificationPojo(@Nullable Integer processed, @Nullable LocalDate order, @Nullable String type, @Nullable Integer service)
    {
        super(processed, order, type);
        this.service = service;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        RoomQueueNotificationPojo.inferenceGsonBuilder(builder);
        return builder;
    }

    @Nullable public Integer getService()
    {
        return this.service;
    }

    public void setService(@Nullable Integer service)
    {
        this.service = service;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServiceQueueNotificationPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        ServiceQueueNotificationPojo that = (ServiceQueueNotificationPojo) o;

        return getService() != null ? getService().equals(that.getService()) : that.getService() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getService() != null ? getService().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "ServiceQueueNotificationPojo{" + "processed=" + processed +
                ", order=" + order +
                ", type='" + type + '\'' +
                ", service=" + service +
                '}';
    }
}
