package com.masbie.travelohealth.pojo.response;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 8:16 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MessagePojo
{
    @SerializedName("notify")
    @Nullable private List<String> notify;

    @SerializedName("message")
    @Nullable private List<String> message;

    public MessagePojo()
    {
    }

    public MessagePojo(@Nullable List<String> notify, @Nullable List<String> message)
    {
        this.notify = notify;
        this.message = message;
    }

    @Nullable public List<String> getNotify()
    {
        return this.notify;
    }

    public void setNotify(@Nullable List<String> notify)
    {
        this.notify = notify;
    }

    @Nullable public List<String> getMessage()
    {
        return this.message;
    }

    public void setMessage(@Nullable List<String> message)
    {
        this.message = message;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof MessagePojo))
        {
            return false;
        }

        MessagePojo that = (MessagePojo) o;

        return getNotify() != null ? getNotify().equals(that.getNotify()) : that.getNotify() == null && (getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null);

    }

    @Override public int hashCode()
    {
        int result = getNotify() != null ? getNotify().hashCode() : 0;
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "MessagePojo{" + "notify=" + notify +
                ", message=" + message +
                '}';
    }
}
