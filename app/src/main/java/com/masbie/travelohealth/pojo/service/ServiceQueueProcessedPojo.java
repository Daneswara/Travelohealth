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
    @Nullable Integer processed;

    public ServiceQueueProcessedPojo()
    {
    }

    public ServiceQueueProcessedPojo(@Nullable Integer processed)
    {
        this.processed = processed;
    }

    public ServiceQueueProcessedPojo(@Nullable Integer id, @Nullable Integer queue, @Nullable LocalDate order, @Nullable LocalDateTime timestamp, @Nullable LocalTime start, @Nullable LocalTime end, @Nullable ServicePojo service, @Nullable DoctorPojo doctor, @Nullable Integer processed)
    {
        super(id, queue, order, timestamp, start, end, service, doctor);
        this.processed = processed;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        ServiceQueuePojo.inferenceGsonBuilder(builder);
    }

    @Nullable public Integer getProcessed()
    {
        return this.processed;
    }

    public void setProcessed(@Nullable Integer processed)
    {
        this.processed = processed;
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

        return getProcessed() != null ? getProcessed().equals(that.getProcessed()) : that.getProcessed() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getProcessed() != null ? getProcessed().hashCode() : 0);
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
                ", processed=" + processed +
                '}';
    }
}
