package com.masbie.travelohealth.pojo.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 7:26 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class FcmTokenPojo
{
    @SerializedName("token")
    @Nullable private String token;

    public FcmTokenPojo(@Nullable String token)
    {
        this.token = token;
    }

    public static void inferenceGsonBuilder(GsonBuilder gsonBuilder)
    {

    }

    @Nullable public String getToken()
    {
        return this.token;
    }

    public void setToken(@Nullable String token)
    {
        this.token = token;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof FcmTokenPojo))
        {
            return false;
        }

        FcmTokenPojo that = (FcmTokenPojo) o;

        return getToken() != null ? getToken().equals(that.getToken()) : that.getToken() == null;

    }

    @Override public int hashCode()
    {
        return getToken() != null ? getToken().hashCode() : 0;
    }

    @Override public String toString()
    {
        return "FcmTokenPojo{" +
                "token='" + token + '\'' +
                '}';
    }
}
