package com.masbie.travelohealth.service.service.get;

/*
 * This <Travelohealth> created by : 
 * Name         : syafiq
 * Date / Time  : 23 October 2017, 7:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

import com.masbie.travelohealth.pojo.response.ResponsePojo;
import com.masbie.travelohealth.pojo.service.DetailedRoomClassPojo;
import com.masbie.travelohealth.pojo.service.DoctorsServicesPojo;
import com.masbie.travelohealth.pojo.service.RoomSectorPojo;
import com.masbie.travelohealth.pojo.service.ServicesDoctorsPojo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InformationService
{
    @GET("/m/api/patient/service/get/service")
    Call<ResponsePojo<List<ServicesDoctorsPojo>>> getService();

    @GET("/m/api/patient/service/get/doctor")
    Call<ResponsePojo<List<DoctorsServicesPojo>>> getDoctor();

    @GET("/m/api/patient/service/get/room")
    Call<ResponsePojo<List<RoomSectorPojo<DetailedRoomClassPojo>>>> getRoom();
}
