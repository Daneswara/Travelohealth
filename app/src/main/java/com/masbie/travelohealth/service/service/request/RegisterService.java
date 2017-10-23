package com.masbie.travelohealth.service.service.request;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 24 October 2017, 5:49 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.ServiceQueuePojo;
import com.masbie.travelohealth.pojo.service.ServiceRequestPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService
{
    @POST("/m/api/patient/service/request/service")
    Call<ResponsePojo<ServiceQueuePojo>> registerService(@Body ServiceRequestPojo service);
}
