package com.masbie.travelohealth.service.service.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 7:27 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.auth.FcmTokenPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FirebaseService
{
    @POST("/m/api/patient/subscribe/register/token")
    Call<ResponsePojo<Void>> registerToken(@Body FcmTokenPojo token);
}
