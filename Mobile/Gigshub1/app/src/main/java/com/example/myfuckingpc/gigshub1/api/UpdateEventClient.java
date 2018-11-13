package com.example.myfuckingpc.gigshub1.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdateEventClient {
    @Multipart
    @POST("/api/event/update")
    Call<ResponseBody> upload(
            @Part("Id") Integer eventId,
            @Part("City")RequestBody city,
            @Part("Address")RequestBody address,
            @Part("Description")RequestBody description,
            @Part("Artist")RequestBody artist,
            @Part("DateTime")RequestBody datetime,
            @Part("IsSale")Boolean isSale,
            @Part("Price")Double price,
            @Part("Category")Integer category,

            @Part MultipartBody.Part img
    );

}
