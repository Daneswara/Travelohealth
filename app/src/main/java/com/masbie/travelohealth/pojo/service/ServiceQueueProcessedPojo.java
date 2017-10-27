package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 02 September 2017, 10:58 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class ServiceQueueProcessedPojo extends ServiceQueuePojo
{
    @Expose
    @Nullable Integer queueProcessed;

    public ServiceQueueProcessedPojo()
    {
    }

    public ServiceQueueProcessedPojo(@Nullable Integer queueProcessed)
    {
        this.queueProcessed = queueProcessed;
    }

    public ServiceQueueProcessedPojo(@Nullable Integer id, @Nullable Integer queue, @Nullable LocalDate order, @Nullable LocalDateTime timestamp, @Nullable LocalTime start, @Nullable LocalTime end, @Nullable ServicePojo service, @Nullable DoctorPojo doctor, @Nullable Integer queueProcessed)
    {
        super(id, queue, order, timestamp, start, end, service, doctor);
        this.queueProcessed = queueProcessed;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        ServiceQueuePojo.inferenceGsonBuilder(builder);
        return builder;
    }

    @Nullable public Integer getQueueProcessed()
    {
        return this.queueProcessed;
    }

    public void setQueueProcessed(@Nullable Integer queueProcessed)
    {
        this.queueProcessed = queueProcessed;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServiceQueueProcessedPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        ServiceQueueProcessedPojo that = (ServiceQueueProcessedPojo) o;

        return getQueueProcessed() != null ? getQueueProcessed().equals(that.getQueueProcessed()) : that.getQueueProcessed() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getQueueProcessed() != null ? getQueueProcessed().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "ServiceQueueProcessedPojo{" +
                "id=" + id +
                ", queue=" + queue +
                ", order=" + order +
                ", timestamp=" + timestamp +
                ", start=" + start +
                ", end=" + end +
                ", service=" + service +
                ", doctor=" + doctor +
                ", queueProcessed=" + queueProcessed +
                '}';
    }
}
