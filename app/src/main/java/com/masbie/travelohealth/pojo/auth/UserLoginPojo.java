package com.masbie.travelohealth.pojo.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 22 October 2017, 9:34 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class UserLoginPojo
{
    @SerializedName("identity")
    @Nullable private String identity;
    @SerializedName("password")
    @Nullable private String password;

    public UserLoginPojo(@Nullable String identity, @Nullable String password)
    {
        this.identity = identity;
        this.password = password;
    }

    public static void inferenceGsonBuilder(GsonBuilder gsonBuilder)
    {

    }

    @Nullable public String getIdentity()
    {
        return this.identity;
    }

    public void setIdentity(@Nullable String identity)
    {
        this.identity = identity;
    }

    @Nullable public String getPassword()
    {
        return this.password;
    }

    public void setPassword(@Nullable String password)
    {
        this.password = password;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof UserLoginPojo))
        {
            return false;
        }

        UserLoginPojo that = (UserLoginPojo) o;

        return getIdentity() != null ? getIdentity().equals(that.getIdentity()) : that.getIdentity() == null && (getPassword() != null ? getPassword().equals(that.getPassword()) : that.getPassword() == null);

    }

    @Override public int hashCode()
    {
        int result = getIdentity() != null ? getIdentity().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "UserLoginPojo{" +
                "identity='" + identity + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
