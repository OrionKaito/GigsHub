package com.example.myfuckingpc.gigshub1.api;

import com.example.myfuckingpc.gigshub1.model.Event;
import com.example.myfuckingpc.gigshub1.model.EventItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventClient {
    @GET("api/event/getall")
    Call<Event> getAll();

    @GET("api/event/getbyid")
    Call<EventItem> getEventById(@Query("Id") long eventId);
}
