package com.masbie.travelohealth.dao.external.personal;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 6:49 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.R;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.pojo.personal.AccountPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.service.service.personal.AccountService;
import com.masbie.travelohealth.util.Setting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class AccountDao
{
    public static Call<ResponsePojo<AccountPojo>> getAccount(final Context context, Callback<ResponsePojo<AccountPojo>> callback)
    {
        Timber.d("getAccount");

        if(callback == null)
        {
            callback = new Callback<ResponsePojo<AccountPojo>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<AccountPojo>> call, @NonNull Response<ResponsePojo<AccountPojo>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<AccountPojo>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        @NonNull final GsonBuilder gsonBuilder = new GsonBuilder();
        AccountPojo.inferenceGsonBuilder(gsonBuilder);

        @NonNull final Retrofit retrofit = Setting.Networking.createDefaultConnection(context, gsonBuilder, true);
        @NonNull final AccountService accountService = retrofit.create(AccountService.class);
        @NonNull final Call<ResponsePojo<AccountPojo>> service = accountService.getAccount();
        service.enqueue(callback);

        return service;
    }

    @SuppressLint("ApplySharedPref") public static void storeAccount(Context context, AccountPojo account)
    {
        Timber.d("storeAccount");

        final SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.shared_prefs_account), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(context.getString(R.string.shared_prefs_account_id), account.getId() == null ? 0 : account.getId());
        editor.putString(context.getString(R.string.shared_prefs_account_username), account.getUsername());
        editor.putString(context.getString(R.string.shared_prefs_account_identity), account.getIdentity());
        editor.commit();
    }
}
