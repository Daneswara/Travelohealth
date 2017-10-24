package com.masbie.travelohealth.dao.external.get;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 7:05 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.dao.external.Dao;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.DetailedRoomClassPojo;
import com.masbie.travelohealth.pojo.service.DoctorsServicesPojo;
import com.masbie.travelohealth.pojo.service.RoomSectorPojo;
import com.masbie.travelohealth.pojo.service.ServicesDoctorsPojo;
import com.masbie.travelohealth.service.service.get.InformationService;
import com.masbie.travelohealth.util.Setting;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class InformationDao
{
    public static Call<ResponsePojo<List<ServicesDoctorsPojo>>> getService(final Context context, Callback<ResponsePojo<List<ServicesDoctorsPojo>>> response)
    {
        Timber.d("getService");

        if(response == null)
        {
            response = new Callback<ResponsePojo<List<ServicesDoctorsPojo>>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<List<ServicesDoctorsPojo>>> call, @NonNull Response<ResponsePojo<List<ServicesDoctorsPojo>>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<List<ServicesDoctorsPojo>>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        GsonBuilder builder = new GsonBuilder();
        ServicesDoctorsPojo.inferenceGsonBuilder(builder);
        @NonNull final Retrofit                       retrofit           = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final InformationService             informationService = retrofit.create(InformationService.class);
        Call<ResponsePojo<List<ServicesDoctorsPojo>>> service            = informationService.getService();
        service.enqueue(response);

        return service;
    }

    public static Call<ResponsePojo<List<DoctorsServicesPojo>>> getDoctor(final Context context, Callback<ResponsePojo<List<DoctorsServicesPojo>>> response)
    {
        Timber.d("registerServiceRequest");

        if(response == null)
        {
            response = new Callback<ResponsePojo<List<DoctorsServicesPojo>>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<List<DoctorsServicesPojo>>> call, @NonNull Response<ResponsePojo<List<DoctorsServicesPojo>>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<List<DoctorsServicesPojo>>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        GsonBuilder builder = new GsonBuilder();
        DoctorsServicesPojo.inferenceGsonBuilder(builder);

        @NonNull final Retrofit                       retrofit           = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final InformationService             informationService = retrofit.create(InformationService.class);
        Call<ResponsePojo<List<DoctorsServicesPojo>>> service            = informationService.getDoctor();
        service.enqueue(response);

        return service;
    }

    public static Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> getRoom(final Context context, Callback<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> response)
    {
        Timber.d("registerServiceRequest");

        if(response == null)
        {
            response = new Callback<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>>()
            {
                @Override public void onResponse(@NonNull Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> call, @NonNull Response<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> response)
                {
                    Dao.defaultSuccessTask(call, response);
                }

                @Override public void onFailure(@NonNull Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> call, @NonNull Throwable throwable)
                {
                    Dao.defaultFailureTask(context, call, throwable);
                }
            };
        }

        GsonBuilder builder = new GsonBuilder();
        DetailedRoomClassPojo.inferenceGsonBuilder(builder);
        RoomSectorPojo.inferenceGsonBuilder(builder);

        @NonNull final Retrofit                                         retrofit           = Setting.Networking.createDefaultConnection(context, builder, true);
        @NonNull final InformationService                               informationService = retrofit.create(InformationService.class);
        Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> service            = informationService.getRoom();
        service.enqueue(response);

        return service;
    }
}
