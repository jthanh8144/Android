package com.example.controlwifi;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/send")
    public Single<Temperature> getTemperature();

    @GET("/up")
    public Single<Item> up();

    @GET("/down")
    public Single<Item> down();

    @GET("/receive")
    public Single<Item> send(@Query(value="percent", encoded=true) String percent);
}
