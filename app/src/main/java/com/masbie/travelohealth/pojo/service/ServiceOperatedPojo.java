package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 8:24 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalTime;

public class ServiceOperatedPojo extends ServicePojo
{
    @SerializedName("opened")
    @Nullable LocalTime operationStart;
    @SerializedName("closed")
    @Nullable LocalTime operationEnd;

    public ServiceOperatedPojo(@Nullable LocalTime operationStart, @Nullable LocalTime operationEnd)
    {
        this.operationStart = operationStart;
        this.operationEnd = operationEnd;
    }

    public ServiceOperatedPojo(@Nullable Integer id, @Nullable String name, @Nullable LocalTime timeStart, @Nullable LocalTime timeEnd, @Nullable LocalTime operationStart, @Nullable LocalTime operationEnd)
    {
        super(id, name, timeStart, timeEnd);
        this.operationStart = operationStart;
        this.operationEnd = operationEnd;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        ServicePojo.inferenceGsonBuilder(builder);
    }

    @Nullable public LocalTime getOperationStart()
    {
        return this.operationStart;
    }

    public void setOperationStart(@Nullable LocalTime operationStart)
    {
        this.operationStart = operationStart;
    }

    @Nullable public LocalTime getOperationEnd()
    {
        return this.operationEnd;
    }

    public void setOperationEnd(@Nullable LocalTime operationEnd)
    {
        this.operationEnd = operationEnd;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ServiceOperatedPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        ServiceOperatedPojo that = (ServiceOperatedPojo) o;

        if(getOperationStart() != null ? !getOperationStart().equals(that.getOperationStart()) : that.getOperationStart() != null)
        {
            return false;
        }
        return getOperationEnd() != null ? getOperationEnd().equals(that.getOperationEnd()) : that.getOperationEnd() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getOperationStart() != null ? getOperationStart().hashCode() : 0);
        result = 31 * result + (getOperationEnd() != null ? getOperationEnd().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "ServiceOperatedPojo{" + "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", timeStart='" + super.getTimeStart() + '\'' +
                ", timeEnd='" + super.getTimeEnd() + '\'' +
                ", operationStart=" + operationStart +
                ", operationEnd=" + operationEnd +
                '}';
    }
}
