package com.masbie.travelohealth.dao.external.request;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 24 October 2017, 5:54 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.RoomQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.RoomRequestPojo;
import com.masbie.travelohealth.pojo.service.ServiceQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.ServiceRequestPojo;
import com.masbie.travelohealth.service.service.request.RegisterService;
import com.masbie.travelohealth.util.Setting;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class RegisterDao
{
    public static Call<ResponsePojo<ServiceQueueProcessedPojo>> registerServiceRequest(ServiceRequestPojo serviceRequest, final Context context, Callback<ResponsePojo<ServiceQueueProcessedPojo>> callback)
    {
        Timber.d("registerServiceRequest");

        if(callback == null)
        {
            callback = new Callback<ResponsePojo<ServiceQueueProcessedPojo>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<ServiceQueueProcessedPojo>> call, @NonNull Response<ResponsePojo<ServiceQueueProcessedPojo>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<ServiceQueueProcessedPojo>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        GsonBuilder builder = new GsonBuilder();
        ServiceRequestPojo.inferenceGsonBuilder(builder);
        ServiceQueueProcessedPojo.inferenceGsonBuilder(builder);

        @NonNull final Retrofit                       retrofit        = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final RegisterService                registerService = retrofit.create(RegisterService.class);
        Call<ResponsePojo<ServiceQueueProcessedPojo>> service         = registerService.registerService(serviceRequest);
        service.enqueue(callback);

        return service;
    }

    public static Call<ResponsePojo<RoomQueueProcessedPojo>> registerRoomRequest(RoomRequestPojo roomRequest, File image, final Context context, Callback<ResponsePojo<RoomQueueProcessedPojo>> callback)
    {
        Timber.d("registerServiceRequest");

        if(callback == null)
        {
            callback = new Callback<ResponsePojo<RoomQueueProcessedPojo>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<RoomQueueProcessedPojo>> call, @NonNull Response<ResponsePojo<RoomQueueProcessedPojo>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<RoomQueueProcessedPojo>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        GsonBuilder builder = new GsonBuilder();
        RoomRequestPojo.inferenceGsonBuilder(builder);
        RoomQueueProcessedPojo.inferenceGsonBuilder(builder);

        Uri                uri         = FileProvider.getUriForFile(context, Setting.getOurInstance().getFileProvider(), image);
        RequestBody        requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(uri)), image);
        MultipartBody.Part imageBody   = MultipartBody.Part.createFormData("validation", image.getName(), requestFile);

        @NonNull final Retrofit                    retrofit        = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final RegisterService             registerService = retrofit.create(RegisterService.class);
        Call<ResponsePojo<RoomQueueProcessedPojo>> service         = registerService.registerRoom(roomRequest.partMap(), imageBody);
        service.enqueue(callback);

        return service;
    }
}
