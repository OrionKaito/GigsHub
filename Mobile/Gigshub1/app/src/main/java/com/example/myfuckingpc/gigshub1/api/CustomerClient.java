package com.example.myfuckingpc.gigshub1.api;

import com.example.myfuckingpc.gigshub1.model.UserInfomation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface CustomerClient {

    @GET("/api/customer/get")
    Call<UserInfomation> getUserInformation();


}
