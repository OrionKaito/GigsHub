package com.example.myfuckingpc.gigshub1.api;

import com.example.myfuckingpc.gigshub1.model.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventClient {
    @GET("api/event/getall")
    Call<List<Event>> getall();
}
