package com.masbie.travelohealth.pojo.response;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 1:46 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

public class ResultPojo<T>
{
    @SerializedName("status")
    @Nullable Integer     status;
    @SerializedName("result")
    @Nullable T           result;
    @SerializedName("message")
    @Nullable MessagePojo message;

    public ResultPojo()
    {
    }

    public ResultPojo(@Nullable Integer status, @Nullable T result, @Nullable MessagePojo message)
    {
        this.status = status;
        this.result = result;
        this.message = message;
    }

    @Nullable public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus(@Nullable Integer status)
    {
        this.status = status;
    }

    @Nullable public T getResult()
    {
        return this.result;
    }

    public void setResult(@Nullable T result)
    {
        this.result = result;
    }

    @Nullable public MessagePojo getMessage()
    {
        return this.message;
    }

    public void setMessage(@Nullable MessagePojo message)
    {
        this.message = message;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof ResultPojo))
        {
            return false;
        }

        ResultPojo resultPojo = (ResultPojo) o;

        return getStatus() != null ? getStatus().equals(resultPojo.getStatus()) : resultPojo.getStatus() == null && (getResult() != null ? getResult().equals(resultPojo.getResult()) : resultPojo.getResult() == null && (message != null ? message.equals(resultPojo.message) : resultPojo.message == null));

    }

    @Override public int hashCode()
    {
        int result1 = getStatus() != null ? getStatus().hashCode() : 0;
        result1 = 31 * result1 + (getResult() != null ? getResult().hashCode() : 0);
        result1 = 31 * result1 + (message != null ? message.hashCode() : 0);
        return result1;
    }

    @Override public String toString()
    {
        return "ResultPojo{" + "status=" + status +
                ", result=" + result +
                ", message=" + message +
                '}';
    }
}
