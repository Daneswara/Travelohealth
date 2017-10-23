package com.masbie.travelohealth.service.service.get;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 7:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.ServicesDoctorsPojo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InformationService
{
    @GET("/m/api/patient/service/get/service")
    Call<ResponsePojo<List<ServicesDoctorsPojo>>> getService();
}
