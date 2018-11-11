package com.example.myfuckingpc.gigshub1.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EditProfileClient {
    @Multipart
    @POST("/api/customer/update")
    Call<ResponseBody> editProfile(
            @Part("Fullname") RequestBody fullname,
            @Part MultipartBody.Part img
    );
}
