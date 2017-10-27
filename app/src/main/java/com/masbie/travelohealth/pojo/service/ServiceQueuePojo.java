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
import com.google.gson.annotations.SerializedName;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalDateDeserialization;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalDateTimeDeserialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalDateSerialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalDateTimeSerialization;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class ServiceQueuePojo
{
    @SerializedName("id")
    @Nullable Integer       id;
    @SerializedName("queue")
    @Nullable Integer       queue;
    @SerializedName("order")
    @Nullable LocalDate     order;
    @SerializedName("create_at")
    @Nullable LocalDateTime timestamp;
    @SerializedName("start")
    @Nullable LocalTime     start;
    @SerializedName("end")
    @Nullable LocalTime     end;
    @SerializedName("service")
    @Nullable ServicePojo   service;
    @SerializedName("doctor")
    @Nullable DoctorPojo    doctor;

    public ServiceQueuePojo()
    {
    }

    public ServiceQueuePojo(@Nullable Integer id, @Nullable Integer queue, @Nullable LocalDate order, @Nullable LocalDateTime timestamp, @Nullable LocalTime start, @Nullable LocalTime end, @Nullable ServicePojo service, @Nullable DoctorPojo doctor)
    {
        this.id = id;
        this.queue = queue;
        this.order = order;
        this.timestamp = timestamp;
        this.start = start;
        this.end = end;
        this.service = service;
        this.doctor = doctor;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        ServicePojo.inferenceGsonBuilder(builder);
        DoctorPojo.inferenceGsonBuilder(builder);
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

    @Nullable public LocalDate getOrder()
    {
        return this.order;
    }

    public void setOrder(@Nullable LocalDate order)
    {
        this.order = order;
    }

    @Nullable public LocalDateTime getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(@Nullable LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    @Nullable public LocalTime getStart()
    {
        return this.start;
    }

    public void setStart(@Nullable LocalTime start)
    {
        this.start = start;
    }

    @Nullable public LocalTime getEnd()
    {
        return this.end;
    }

    public void setEnd(@Nullable LocalTime end)
    {
        this.end = end;
    }

    @Nullable public ServicePojo getService()
    {
        return this.service;
    }

    public void setService(@Nullable ServicePojo service)
    {
        this.service = service;
    }

    @Nullable public DoctorPojo getDoctor()
    {
        return this.doctor;
    }

    public void setDoctor(@Nullable DoctorPojo doctor)
    {
        this.doctor = doctor;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServiceQueuePojo))
        {
            return false;
        }

        ServiceQueuePojo that = (ServiceQueuePojo) o;

        if(getId() != null ? !getId().equals(that.getId()) : that.getId() != null)
        {
            return false;
        }
        if(getQueue() != null ? !getQueue().equals(that.getQueue()) : that.getQueue() != null)
        {
            return false;
        }
        if(getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null)
        {
            return false;
        }
        if(getTimestamp() != null ? !getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() != null)
        {
            return false;
        }
        if(getStart() != null ? !getStart().equals(that.getStart()) : that.getStart() != null)
        {
            return false;
        }
        if(getEnd() != null ? !getEnd().equals(that.getEnd()) : that.getEnd() != null)
        {
            return false;
        }
        if(getService() != null ? !getService().equals(that.getService()) : that.getService() != null)
        {
            return false;
        }
        return getDoctor() != null ? getDoctor().equals(that.getDoctor()) : that.getDoctor() == null;

    }

    @Override public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getQueue() != null ? getQueue().hashCode() : 0);
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getService() != null ? getService().hashCode() : 0);
        result = 31 * result + (getDoctor() != null ? getDoctor().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "ServiceQueuePojo{" +
                "id=" + id +
                ", queue=" + queue +
                ", order=" + order +
                ", timestamp=" + timestamp +
                ", start=" + start +
                ", end=" + end +
                ", service=" + service +
                ", doctor=" + doctor +
                '}';
    }
}
