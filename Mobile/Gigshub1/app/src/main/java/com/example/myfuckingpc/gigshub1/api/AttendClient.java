package com.example.myfuckingpc.gigshub1.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AttendClient {

    @POST("/api/attendance/attend")
    Call<ResponseBody> attend(@Query("eventId") long eventId);
}
