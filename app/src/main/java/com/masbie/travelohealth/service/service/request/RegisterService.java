package com.masbie.travelohealth.service.service.request;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 24 October 2017, 5:49 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.RoomQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.ServiceQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.ServiceRequestPojo;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RegisterService
{
    @POST("/m/api/patient/service/request/service")
    Call<ResponsePojo<ServiceQueueProcessedPojo>> registerService(@Body ServiceRequestPojo service);

    @Multipart
    @POST("/m/api/patient/service/request/room")
    Call<ResponsePojo<RoomQueueProcessedPojo>> registerRoom(@PartMap() Map<String, RequestBody> data, @Part MultipartBody.Part file);

}
