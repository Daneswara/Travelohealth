package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 8:30 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.joda.time.LocalTime;

public class DoctorsServicesPojo extends DoctorPojo
{
    @SerializedName("service")
    @Nullable List<ServiceOperatedPojo> services;

    public DoctorsServicesPojo(@Nullable List<ServiceOperatedPojo> services)
    {
        this.services = services;
    }

    public DoctorsServicesPojo(@Nullable Integer id, @Nullable String name, @Nullable LocalTime timeStart, @Nullable LocalTime timeEnd, @Nullable List<ServiceOperatedPojo> services)
    {
        super(id, name, timeStart, timeEnd);
        this.services = services;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        DoctorPojo.inferenceGsonBuilder(builder);
        ServiceOperatedPojo.inferenceGsonBuilder(builder);
    }

    @Nullable public List<ServiceOperatedPojo> getServices()
    {
        return this.services;
    }

    public void setServices(@Nullable List<ServiceOperatedPojo> services)
    {
        this.services = services;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof DoctorsServicesPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        DoctorsServicesPojo that = (DoctorsServicesPojo) o;

        return getServices() != null ? getServices().equals(that.getServices()) : that.getServices() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getServices() != null ? getServices().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "DoctorsServicesPojo{" + "id=" + id +
                ", name='" + name + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", services=" + services +
                '}';
    }
}
