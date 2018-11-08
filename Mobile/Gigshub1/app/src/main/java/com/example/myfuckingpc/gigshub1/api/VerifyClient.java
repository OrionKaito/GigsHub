package com.example.myfuckingpc.gigshub1.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VerifyClient {
    @FormUrlEncoded
    @POST("/api/customer/confirmemail")
    Call<ResponseBody> verify(@Field("name") String username, @Field("Code")String code);
}
