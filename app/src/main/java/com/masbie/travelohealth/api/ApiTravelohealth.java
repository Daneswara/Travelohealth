package com.masbie.travelohealth.api;

import com.masbie.travelohealth.ObjectDoctor.Doctor;
import com.masbie.travelohealth.ObjectLogin.Login;
import com.masbie.travelohealth.ObjectPelayanan.Poli;
import com.masbie.travelohealth.ObjectRoom.Room;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Daneswara Jauhari on 29/08/2017.
 */
public interface ApiTravelohealth {

    @FormUrlEncoded
    @POST("patient/auth/login")
    Call<Login> postLogin(@FieldMap HashMap<String, String> params);


    @GET("patient/service/get/room")
    Call<Room> showRoom(@HeaderMap Map<String, String> headers);

    @GET("patient/service/get/doctor")
    Call<Doctor> showDoctor(@HeaderMap Map<String, String> headers);

    @GET("patient/service/get/service")
    Call<Poli> showPoli(@HeaderMap Map<String, String> headers);
}
