package com.masbie.travelohealth.dao;

/*
 * This <client-android-travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 25 August 2017, 12:24 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import com.masbie.travelohealth.R;
import com.masbie.travelohealth.pojo.auth.TokenPojo;
import timber.log.Timber;

@SuppressLint("ApplySharedPref") public class TokenDao
{
    @Nullable public static TokenPojo retrieveToken(Context context)
    {
        Timber.d("retrieveToken");

        final SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_prefs_token), Context.MODE_PRIVATE);
        final String            token      = sharedPref.getString(context.getString(R.string.shared_prefs_token_token), null);
        final String            refresh    = sharedPref.getString(context.getString(R.string.shared_prefs_token_refresh), null);
        return token != null ? new TokenPojo(token, refresh) : null;
    }

    public static void storeToken(Context context, TokenPojo tokenPojo)
    {
        Timber.d("storeToken");

        final SharedPreferences        sharedPref = context.getSharedPreferences(context.getString(R.string.shared_prefs_token), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor     = sharedPref.edit();
        editor.putString(context.getString(R.string.shared_prefs_token_token), tokenPojo.getToken());
        editor.putString(context.getString(R.string.shared_prefs_token_refresh), tokenPojo.getRefresh());
        editor.commit();
    }

    public static void clear(Context context)
    {
        final SharedPreferences        sharedPref = context.getSharedPreferences(context.getString(R.string.shared_prefs_token), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor     = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
}
