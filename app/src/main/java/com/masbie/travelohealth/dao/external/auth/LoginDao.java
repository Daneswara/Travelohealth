package com.masbie.travelohealth.dao.external.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 22 October 2017, 10:37 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.pojo.auth.TokenPojo;
import com.masbie.travelohealth.pojo.auth.UserLoginPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.service.service.auth.LoginService;
import com.masbie.travelohealth.util.Setting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class LoginDao
{
    public static Call<ResponsePojo<TokenPojo>> login(@NonNull UserLoginPojo userCredential, final @NonNull Context context, @Nullable Callback<ResponsePojo<TokenPojo>> callback)
    {
        Timber.d("login");

        if(callback == null)
        {
            callback = new Callback<ResponsePojo<TokenPojo>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<TokenPojo>> call, @NonNull Response<ResponsePojo<TokenPojo>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<TokenPojo>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        @NonNull GsonBuilder gsonBuilder = new GsonBuilder();
        UserLoginPojo.inferenceGsonBuilder(gsonBuilder);

        @NonNull final Retrofit                      retrofit     = Setting.Networking.createDefaultConnection(context, gsonBuilder, false);
        @NonNull final LoginService                  loginService = retrofit.create(LoginService.class);
        @NonNull final Call<ResponsePojo<TokenPojo>> service      = loginService.login(userCredential);
        service.enqueue(callback);

        return service;
    }

    public static Call<ResponsePojo<Void>> ping(final @NonNull Context context, @Nullable Callback<ResponsePojo<Void>> callback)
    {
        Timber.d("ping");

        if(callback == null)
        {
            callback = new Callback<ResponsePojo<Void>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<Void>> call, @NonNull Response<ResponsePojo<Void>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<Void>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        @NonNull GsonBuilder gsonBuilder = new GsonBuilder();

        @NonNull final Retrofit                 retrofit     = Setting.Networking.createDefaultConnection(context, gsonBuilder, true);
        @NonNull final LoginService             loginService = retrofit.create(LoginService.class);
        @NonNull final Call<ResponsePojo<Void>> service      = loginService.ping();
        service.enqueue(callback);

        return service;
    }
}
