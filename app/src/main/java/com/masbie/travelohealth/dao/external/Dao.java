package com.masbie.travelohealth.dao.external;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 6:51 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

@SuppressWarnings("ConstantConditions") public class Dao
{
    public static void defaultSuccessTask(@NonNull Call call, @NonNull Response response)
    {
        Timber.d("defaultSuccessTask");

        if((response.body() != null) && (response.body() instanceof ResponsePojo))
        {
            Timber.d(String.valueOf(((ResponsePojo) response.body()).getData()));
        }
        else
        {
            Timber.d(String.valueOf(response.body()));
        }
    }

    public static void defaultFailureTask(@NonNull final Context context, @NonNull Call call, @NonNull Throwable throwable)
    {
        Timber.d("defaultFailureTask");
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

        if(call.isCanceled())
        {
            Timber.e("request was cancelled");
        }
        else
        {
            Timber.e(throwable);
        }
    }
}
