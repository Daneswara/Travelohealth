package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 3:04 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class DetailedRoomClassPojo extends RoomClassPojo
{
    @SerializedName("capacity")
    @Nullable Integer capacity;
    @SerializedName("ready")
    @Nullable Integer ready;

    public DetailedRoomClassPojo(@Nullable Integer capacity, @Nullable Integer ready)
    {
        this.capacity = capacity;
        this.ready = ready;
    }

    public DetailedRoomClassPojo(@Nullable Integer id, @Nullable String name, @Nullable Double cost, @Nullable String feature, @Nullable String preview, Integer capacity, Integer ready)
    {
        super(id, name, cost, feature, preview);
        this.capacity = capacity;
        this.ready = ready;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        RoomClassPojo.inferenceGsonBuilder(builder);
    }

    @Nullable public Integer getCapacity()
    {
        return this.capacity;
    }

    public void setCapacity(@Nullable Integer capacity)
    {
        this.capacity = capacity;
    }

    @Nullable public Integer getReady()
    {
        return this.ready;
    }

    public void setReady(@Nullable Integer ready)
    {
        this.ready = ready;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof DetailedRoomClassPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        DetailedRoomClassPojo that = (DetailedRoomClassPojo) o;

        if(getCapacity() != null ? !getCapacity().equals(that.getCapacity()) : that.getCapacity() != null)
        {
            return false;
        }
        return getReady() != null ? getReady().equals(that.getReady()) : that.getReady() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getCapacity() != null ? getCapacity().hashCode() : 0);
        result = 31 * result + (getReady() != null ? getReady().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "DetailedRoomClassPojo{" + "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", cost=" + super.getCost() +
                ", feature='" + super.getFeature() + '\'' +
                ", capacity=" + capacity +
                ", ready=" + ready +
                '}';
    }
}
