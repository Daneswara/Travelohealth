package com.masbie.travelohealth.service.service.personal;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 6:50 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.personal.AccountPojo;
import com.masbie.travelohealth.pojo.response.ResponsePojo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AccountService
{
    @GET("/m/api/patient/personal/account/find?code=0")
    Call<ResponsePojo<AccountPojo>> getAccount();
}
