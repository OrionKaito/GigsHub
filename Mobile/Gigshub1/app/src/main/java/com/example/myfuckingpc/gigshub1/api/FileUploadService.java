package com.example.myfuckingpc.gigshub1.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {
    @Multipart
    @POST("/api/event/create")
    Call<ResponseBody> upload(
            @Part("Id") RequestBody id,
            @Part("Name") RequestBody name,
            @Part("Title") RequestBody title,
            @Part("Location") RequestBody location,
            @Part("Description") RequestBody description,
            @Part MultipartBody.Part img
    );
}
