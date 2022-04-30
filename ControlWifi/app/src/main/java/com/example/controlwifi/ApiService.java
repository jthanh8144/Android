package com.example.controlwifi;

import android.content.Context;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final String BASE_URL = "http://192.168.1.9/";
    private static Api api;
    private static ApiService instance;

    private ApiService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    public static ApiService getInstance(Context context) {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public Single<Temperature> getTemperature() {
        return api.getTemperature();
    }
    public Single<Item> up() { return api.up(); }
    public Single<Item> down() { return api.down(); }
    public Single<Item> send(String percent) { return api.send(percent); }
}
