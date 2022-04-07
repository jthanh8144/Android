package com.example.midtermtest.viewmodel;

import com.example.midtermtest.model.Item;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface Api {
    @GET("DevTides/DogsApi/master/dogs.json")
    public Single<List<Item>> getItems();
}
