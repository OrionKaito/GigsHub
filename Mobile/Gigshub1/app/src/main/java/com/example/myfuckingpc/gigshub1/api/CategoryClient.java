package com.example.myfuckingpc.gigshub1.api;

import com.example.myfuckingpc.gigshub1.model.Category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryClient {
    @GET("/api/eventcategory/getall")
    Call<Category> getAllCategory();
}
