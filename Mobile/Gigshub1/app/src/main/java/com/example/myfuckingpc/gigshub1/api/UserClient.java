package com.example.myfuckingpc.gigshub1.api;

import com.example.myfuckingpc.gigshub1.model.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserClient {

    @FormUrlEncoded
    @POST("/token")
    Call<User> login(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("/api/Account/Register")
    Call<ResponseBody> register(@Field("Email") String email, @Field("Username") String username,@Field("Fullname") String fullname, @Field("Password") String password, @Field("ConfirmPassword") String confirmPassword);


}
