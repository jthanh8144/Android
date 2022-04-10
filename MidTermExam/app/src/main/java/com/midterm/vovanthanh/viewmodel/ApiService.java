package com.midterm.vovanthanh.viewmodel;

import android.content.Context;

import com.midterm.vovanthanh.model.Item;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final String BASE_URL = "https://api.jsonbin.io/";
    private Api api;
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

    public Single<List<Item>> getItems() {
        return api.getItems();
    }
}
