package com.masbie.travelohealth.pojo.personal;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 6:47 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class AccountPojo
{
    @SerializedName("id")
    @Nullable private Integer id;
    @SerializedName("username")
    @Nullable private String  username;
    @SerializedName("coupon")
    @Nullable private String  identity;

    public AccountPojo(@Nullable Integer id, @Nullable String username, @Nullable String identity)
    {
        this.id = id;
        this.username = username;
        this.identity = identity;
    }

    public static void inferenceGsonBuilder(GsonBuilder gsonBuilder)
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

    @Nullable public String getUsername()
    {
        return this.username;
    }

    public void setUsername(@Nullable String username)
    {
        this.username = username;
    }

    @Nullable public String getIdentity()
    {
        return this.identity;
    }

    public void setIdentity(@Nullable String identity)
    {
        this.identity = identity;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof AccountPojo))
        {
            return false;
        }

        AccountPojo that = (AccountPojo) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && (getUsername() != null ? getUsername().equals(that.getUsername()) : that.getUsername() == null && (getIdentity() != null ? getIdentity().equals(that.getIdentity()) : that.getIdentity() == null));

    }

    @Override public int hashCode()
    {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getIdentity() != null ? getIdentity().hashCode() : 0);
        return result;
    }

    @Override public String toString()
    {
        return "AccountPojo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", identity='" + identity + '\'' +
                '}';
    }
}
