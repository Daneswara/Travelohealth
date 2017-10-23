package com.masbie.travelohealth.service.service.auth;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 22 October 2017, 9:32 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.auth.TokenPojo;
import com.masbie.travelohealth.pojo.auth.UserLoginPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService
{
    @POST("/m/api/patient/auth/login")
    Call<ResponsePojo<TokenPojo>> login(@Body UserLoginPojo service);
}
