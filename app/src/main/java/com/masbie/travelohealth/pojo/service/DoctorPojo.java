package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 2:12 PM.
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

public class DoctorPojo
{
    @SerializedName("id")
    @Nullable Integer id;
    @SerializedName(value = "username", alternate = {"name"})
    @Nullable String name;
    @SerializedName("start")
    @Nullable LocalTime timeStart;
    @SerializedName("end")
    @Nullable LocalTime timeEnd;

    public DoctorPojo()
    {
    }

    public DoctorPojo(@Nullable Integer id, @Nullable String name, @Nullable LocalTime timeStart, @Nullable LocalTime timeEnd)
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
        if(!(o instanceof DoctorPojo))
        {
            return false;
        }

        DoctorPojo that = (DoctorPojo) o;

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
        return "DoctorPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}
