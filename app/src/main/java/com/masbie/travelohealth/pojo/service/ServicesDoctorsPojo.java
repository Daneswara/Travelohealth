package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 2:19 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.LocalTime;

public class ServicesDoctorsPojo extends ServicePojo
{
    @SerializedName("doctors")
    @Nullable List<DoctorPojo> doctors;

    public ServicesDoctorsPojo(@Nullable List<DoctorPojo> doctors)
    {
        this.doctors = doctors;
    }

    public ServicesDoctorsPojo(@Nullable Integer id, @Nullable String name, @Nullable LocalTime timeStart, @Nullable LocalTime timeEnd, @Nullable List<DoctorPojo> doctors)
    {
        super(id, name, timeStart, timeEnd);
        this.doctors = doctors;
    }

    public static void inferenceGsonBuilder(@NotNull GsonBuilder builder)
    {
        ServicePojo.inferenceGsonBuilder(builder);
        DoctorPojo.inferenceGsonBuilder(builder);
    }

    @Nullable public List<DoctorPojo> getDoctors()
    {
        return this.doctors;
    }

    public void setDoctors(@Nullable List<DoctorPojo> doctors)
    {
        this.doctors = doctors;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServicesDoctorsPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        ServicesDoctorsPojo that = (ServicesDoctorsPojo) o;

        return getDoctors() != null ? getDoctors().equals(that.getDoctors()) : that.getDoctors() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getDoctors() != null ? getDoctors().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "ServicesDoctorsPojo{" + "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", timeStart='" + getTimeStart() + '\'' +
                ", timeEnd='" + getTimeEnd() + '\'' +
                ", doctors=" + doctors +
                '}';
    }
}
