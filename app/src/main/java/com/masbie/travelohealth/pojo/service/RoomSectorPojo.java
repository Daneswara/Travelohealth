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
import java.util.List;

public class RoomSectorPojo<T extends RoomClassPojo> extends RoomPojo
{
    @SerializedName("classes")
    @Nullable List<T> classes;

    public RoomSectorPojo(@Nullable List<T> classes)
    {
        this.classes = classes;
    }

    public RoomSectorPojo(@Nullable Integer id, @Nullable String name, @Nullable List<T> classes)
    {
        super(id, name);
        this.classes = classes;
    }

    public static GsonBuilder inferenceGsonBuilder(@NonNull GsonBuilder builder)
    {
        RoomClassPojo.inferenceGsonBuilder(builder);
        RoomPojo.inferenceGsonBuilder(builder);
        return builder;
    }

    @Nullable public List<T> getClasses()
    {
        return this.classes;
    }

    public void setClasses(@Nullable List<T> classes)
    {
        this.classes = classes;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof RoomSectorPojo))
        {
            return false;
        }
        if(!super.equals(o))
        {
            return false;
        }

        RoomSectorPojo<?> that = (RoomSectorPojo<?>) o;

        return classes != null ? classes.equals(that.classes) : that.classes == null;

    }

    @Override public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (classes != null ? classes.hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "RoomSectorPojo{" + "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", classes=" + classes +
                '}';
    }
}
