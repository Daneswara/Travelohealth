package com.masbie.travelohealth.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Daneswara Jauhari on 29/08/2017.
 */
public interface ApiLogin {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> postMessage(@FieldMap HashMap<String, String> params);
}
