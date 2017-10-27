package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 2:59 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class RoomPojo
{
    @SerializedName("id")
    @Nullable Integer id;
    @SerializedName("name")
    @Nullable String  name;

    public RoomPojo()
    {
    }

    public RoomPojo(@Nullable Integer id, @Nullable String name)
    {
        this.id = id;
        this.name = name;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
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

    @Nullable public String getName()
    {
        return this.name;
    }

    public void setName(@Nullable String name)
    {
        this.name = name;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomPojo))
        {
            return false;
        }

        RoomPojo roomPojo = (RoomPojo) o;

        if(getId() != null ? !getId().equals(roomPojo.getId()) : roomPojo.getId() != null)
        {
            return false;
        }
        return getName() != null ? getName().equals(roomPojo.getName()) : roomPojo.getName() == null;

    }

    @Override public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomPojo{" + "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
