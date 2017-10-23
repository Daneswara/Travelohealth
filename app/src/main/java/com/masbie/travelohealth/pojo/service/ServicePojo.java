package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 12:51 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.masbie.travelohealth.custom.gson.deserialization.JodaLocalTimeDeserialization;
import com.masbie.travelohealth.custom.gson.serialization.JodaLocalTimeSerialization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.LocalTime;

public class ServicePojo
{
    @SerializedName("id")
    @Nullable private Integer id;
    @SerializedName("name")
    @Nullable private String name;
    @SerializedName("start")
    @Nullable private LocalTime timeStart;
    @SerializedName("end")
    @Nullable private LocalTime timeEnd;

    public ServicePojo()
    {
    }

    public ServicePojo(@Nullable Integer id, @Nullable String name, @Nullable LocalTime timeStart, @Nullable LocalTime timeEnd)
    {
        this.id = id;
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public static void inferenceGsonBuilder(@NotNull GsonBuilder builder)
    {
        builder.registerTypeAdapter(LocalTime.class, new JodaLocalTimeSerialization());
        builder.registerTypeAdapter(LocalTime.class, new JodaLocalTimeDeserialization());
    }

    @Nullable public Integer getId()
    {
        return this.id;
    }

    public void setId(@Nullable Integer id)
    {
        this.id = id;
    }

    @Nullable public String getName()
    {
        return this.name;
    }

    public void setName(@Nullable String name)
    {
        this.name = name;
    }

    @Nullable public LocalTime getTimeStart()
    {
        return this.timeStart;
    }

    public void setTimeStart(@Nullable LocalTime timeStart)
    {
        this.timeStart = timeStart;
    }

    @Nullable public LocalTime getTimeEnd()
    {
        return this.timeEnd;
    }

    public void setTimeEnd(@Nullable LocalTime timeEnd)
    {
        this.timeEnd = timeEnd;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServicePojo))
        {
            return false;
        }

        ServicePojo that = (ServicePojo) o;

        if(getId() != null ? !getId().equals(that.getId()) : that.getId() != null)
        {
            return false;
        }
        if(getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
        {
            return false;
        }
        if(getTimeStart() != null ? !getTimeStart().equals(that.getTimeStart()) : that.getTimeStart() != null)
        {
            return false;
        }
        return getTimeEnd() != null ? getTimeEnd().equals(that.getTimeEnd()) : that.getTimeEnd() == null;

    }

    @Override public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getTimeStart() != null ? getTimeStart().hashCode() : 0);
        result = 31 * result + (getTimeEnd() != null ? getTimeEnd().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "ServicePojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}
