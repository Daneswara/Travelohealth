package com.masbie.travelohealth.pojo.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 22 October 2017, 9:37 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class TokenPojo
{
    @SerializedName("token")
    @Nullable private String token;
    @SerializedName("refresh")
    @Nullable private String refresh;

    public TokenPojo(@Nullable String token, @Nullable String refresh)
    {
        this.token = token;
        this.refresh = refresh;
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

    @Nullable public String getRefresh()
    {
        return this.refresh;
    }

    public void setRefresh(@Nullable String refresh)
    {
        this.refresh = refresh;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof TokenPojo))
        {
            return false;
        }

        TokenPojo tokenPojo = (TokenPojo) o;

        return getToken() != null ? getToken().equals(tokenPojo.getToken()) : tokenPojo.getToken() == null && (getRefresh() != null ? getRefresh().equals(tokenPojo.getRefresh()) : tokenPojo.getRefresh() == null);

    }

    @Override public int hashCode()
    {
        int result = getToken() != null ? getToken().hashCode() : 0;
        result = 31 * result + (getRefresh() != null ? getRefresh().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "TokenPojo{" +
                "token='" + token + '\'' +
                ", refresh='" + refresh + '\'' +
                '}';
    }
}
