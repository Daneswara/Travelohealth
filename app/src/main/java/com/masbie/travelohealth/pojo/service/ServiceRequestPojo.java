package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 3:16 PM.
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

public class ServiceRequestPojo
{
    @SerializedName("id")
    @Nullable Integer   doctorId;
    @SerializedName("date")
    @Nullable LocalDate date;

    public ServiceRequestPojo(@Nullable Integer doctorId, @Nullable LocalDate date)
    {
        this.doctorId = doctorId;
        this.date = date;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateSerialization());
        builder.registerTypeAdapter(LocalDate.class, new JodaLocalDateDeserialization());
        return builder;
    }

    @Nullable public Integer getDoctorId()
    {
        return this.doctorId;
    }

    public void setDoctorId(@Nullable Integer doctorId)
    {
        this.doctorId = doctorId;
    }

    @Nullable public LocalDate getDate()
    {
        return this.date;
    }

    public void setDate(@Nullable LocalDate date)
    {
        this.date = date;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServiceRequestPojo))
        {
            return false;
        }

        ServiceRequestPojo that = (ServiceRequestPojo) o;

        if(getDoctorId() != null ? !getDoctorId().equals(that.getDoctorId()) : that.getDoctorId() != null)
        {
            return false;
        }
        return getDate() != null ? getDate().equals(that.getDate()) : that.getDate() == null;

    }

    @Override public int hashCode()
    {
        int result = getDoctorId() != null ? getDoctorId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }


}
