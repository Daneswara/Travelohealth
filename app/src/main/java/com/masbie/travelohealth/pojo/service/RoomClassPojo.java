package com.masbie.travelohealth.pojo.service;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 03 September 2017, 3:00 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class RoomClassPojo
{
    @SerializedName("id")
    @Nullable Integer id;
    @SerializedName("name")
    @Nullable String  name;
    @SerializedName("cost")
    @Nullable Double  cost;
    @SerializedName("feature")
    @Nullable String  feature;
    @SerializedName("preview")
    @Nullable String  preview;

    public RoomClassPojo()
    {
    }

    public RoomClassPojo(@Nullable Integer id, @Nullable String name, @Nullable Double cost, @Nullable String feature, @Nullable String preview)
    {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.feature = feature;
        this.preview = preview;
    }

    public static void inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
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

    @Nullable public Double getCost()
    {
        return this.cost;
    }

    public void setCost(@Nullable Double cost)
    {
        this.cost = cost;
    }

    @Nullable public String getFeature()
    {
        return this.feature;
    }

    public void setFeature(@Nullable String feature)
    {
        this.feature = feature;
    }

    @Nullable public String getPreview()
    {
        return this.preview;
    }

    public void setPreview(@Nullable String preview)
    {
        this.preview = preview;
    }

    @SuppressWarnings("SimplifiableIfStatement") @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomClassPojo))
        {
            return false;
        }

        RoomClassPojo that = (RoomClassPojo) o;

        if(getId() != null ? !getId().equals(that.getId()) : that.getId() != null)
        {
            return false;
        }
        if(getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
        {
            return false;
        }
        if(getCost() != null ? !getCost().equals(that.getCost()) : that.getCost() != null)
        {
            return false;
        }
        if(getFeature() != null ? !getFeature().equals(that.getFeature()) : that.getFeature() != null)
        {
            return false;
        }
        return getPreview() != null ? getPreview().equals(that.getPreview()) : that.getPreview() == null;

    }

    @Override public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + (getFeature() != null ? getFeature().hashCode() : 0);
        result = 31 * result + (getPreview() != null ? getPreview().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomClassPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", feature='" + feature + '\'' +
                ", preview='" + preview + '\'' +
                '}';
    }
}
