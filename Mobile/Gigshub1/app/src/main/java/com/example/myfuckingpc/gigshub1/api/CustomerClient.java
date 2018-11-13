package com.example.myfuckingpc.gigshub1.api;

import com.example.myfuckingpc.gigshub1.model.UserLoginInformation;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CustomerClient {

    @GET("/api/customer/get")
    Call<UserLoginInformation> getUserInformation();


}
