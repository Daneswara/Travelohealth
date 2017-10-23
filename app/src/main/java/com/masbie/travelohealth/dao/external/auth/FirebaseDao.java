package com.masbie.travelohealth.dao.external.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 7:32 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.pojo.auth.FcmTokenPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.service.service.auth.FirebaseService;
import com.masbie.travelohealth.util.Setting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class FirebaseDao
{
    public static Call<ResponsePojo<Void>> registerToken(@NonNull final FcmTokenPojo token, final Context context, Callback<ResponsePojo<Void>> callback)
    {
        Timber.d("registerToken");

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

        @NonNull final GsonBuilder builder = new GsonBuilder();
        FcmTokenPojo.inferenceGsonBuilder(builder);

        @NonNull final Retrofit retrofit = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final FirebaseService firebaseService = retrofit.create(FirebaseService.class);
        @NonNull final Call<ResponsePojo<Void>> service = firebaseService.registerToken(token);
        service.enqueue(callback);

        return service;
    }
}
