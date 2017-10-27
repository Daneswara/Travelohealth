package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 06 September 2017, 5:33 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class RoomSummaryPojo extends RoomPojo
{
    @SerializedName("class")
    @Nullable String  roomClass;
    @SerializedName("sector")
    @Nullable String  sector;
    @SerializedName("feature")
    @Nullable String  feature;
    @SerializedName("cost")
    @Nullable Integer cost;

    public RoomSummaryPojo()
    {
    }

    public RoomSummaryPojo(@Nullable String roomClass, @Nullable String sector, @Nullable String feature, @Nullable Integer cost)
    {
        this.roomClass = roomClass;
        this.sector = sector;
        this.feature = feature;
        this.cost = cost;
    }

    public RoomSummaryPojo(@Nullable Integer id, @Nullable String name, @Nullable String roomClass, @Nullable String sector, @Nullable String feature, @Nullable Integer cost)
    {
        super(id, name);
        this.roomClass = roomClass;
        this.sector = sector;
        this.feature = feature;
        this.cost = cost;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        RoomPojo.inferenceGsonBuilder(builder);
        return builder;
    }

    @Nullable public String getRoomClass()
    {
        return this.roomClass;
    }

    public void setRoomClass(@Nullable String roomClass)
    {
        this.roomClass = roomClass;
    }

    @Nullable public String getSector()
    {
        return this.sector;
    }

    public void setSector(@Nullable String sector)
    {
        this.sector = sector;
    }

    @Nullable public String getFeature()
    {
        return this.feature;
    }

    public void setFeature(@Nullable String feature)
    {
        this.feature = feature;
    }

    @Nullable public Integer getCost()
    {
        return this.cost;
    }

    public void setCost(@Nullable Integer cost)
    {
        this.cost = cost;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomSummaryPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        RoomSummaryPojo that = (RoomSummaryPojo) o;

        if(getRoomClass() != null ? !getRoomClass().equals(that.getRoomClass()) : that.getRoomClass() != null)
        {
            return false;
        }
        if(getSector() != null ? !getSector().equals(that.getSector()) : that.getSector() != null)
        {
            return false;
        }
        if(getFeature() != null ? !getFeature().equals(that.getFeature()) : that.getFeature() != null)
        {
            return false;
        }
        return getCost() != null ? getCost().equals(that.getCost()) : that.getCost() == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getRoomClass() != null ? getRoomClass().hashCode() : 0);
        result = 31 * result + (getSector() != null ? getSector().hashCode() : 0);
        result = 31 * result + (getFeature() != null ? getFeature().hashCode() : 0);
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomSummaryPojo{" + "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", class='" + roomClass + '\'' +
                ", sector='" + sector + '\'' +
                ", feature='" + feature + '\'' +
                ", cost=" + cost +
                '}';
    }
}
