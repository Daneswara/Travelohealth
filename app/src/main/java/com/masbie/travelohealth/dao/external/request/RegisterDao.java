package com.masbie.travelohealth.dao.external.request;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 24 October 2017, 5:54 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.ServiceQueuePojo;
import com.masbie.travelohealth.pojo.service.ServiceRequestPojo;
import com.masbie.travelohealth.service.service.request.RegisterService;
import com.masbie.travelohealth.util.Setting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class RegisterDao
{
    public static Call<ResponsePojo<ServiceQueuePojo>> registerServiceRequest(ServiceRequestPojo serviceRequest, final Context context, Callback<ResponsePojo<ServiceQueuePojo>> callback)
    {
        Timber.d("registerServiceRequest");

        if(callback == null)
        {
            callback = new Callback<ResponsePojo<ServiceQueuePojo>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<ServiceQueuePojo>> call, @NonNull Response<ResponsePojo<ServiceQueuePojo>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<ServiceQueuePojo>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        GsonBuilder builder = new GsonBuilder();
        ServiceRequestPojo.inferenceGsonBuilder(builder);
        ServiceQueuePojo.inferenceGsonBuilder(builder);

        @NonNull final Retrofit              retrofit        = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final RegisterService       registerService = retrofit.create(RegisterService.class);
        Call<ResponsePojo<ServiceQueuePojo>> service         = registerService.registerService(serviceRequest);
        service.enqueue(callback);

        return service;
    }
}
