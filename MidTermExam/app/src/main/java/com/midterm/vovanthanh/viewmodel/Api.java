package com.midterm.vovanthanh.viewmodel;

import com.midterm.vovanthanh.model.Item;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface Api {
    @GET("b/625039f3d20ace068f9580fb")
    public Single<List<Item>> getItems();
}