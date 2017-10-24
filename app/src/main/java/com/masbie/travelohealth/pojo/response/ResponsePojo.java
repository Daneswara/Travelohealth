package com.masbie.travelohealth.pojo.response;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 12:42 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;

public class ResponsePojo<T>
{
    @SerializedName("data")
    @Nullable private ResultPojo<T> data;

    @SerializedName("status")
    @Nullable private Integer status;

    public ResponsePojo(@Nullable ResultPojo<T> data, @Nullable Integer status)
    {
        this.data = data;
        this.status = status;
    }

    public ResponsePojo()
    {
    }

    @Nullable public ResultPojo<T> getData()
    {
        return this.data;
    }

    public void setData(@Nullable ResultPojo<T> data)
    {
        this.data = data;
    }

    @Nullable public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus(@Nullable Integer status)
    {
        this.status = status;
    }


    @Override public String toString()
    {
        return "ResponsePojo{" + "data=" + data +
                ", status=" + status +
                '}';
    }
}
