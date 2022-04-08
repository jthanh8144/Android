package com.example.midtermtest.viewmodel;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.*;

import com.example.midtermtest.model.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "items")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
            if (!context.getDatabasePath("items").exists()) {
                Log.d("DEBUGDB", "no");
                ApiService apiService = ApiService.getInstance(context);
                apiService.getItems()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Item>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Item> items) {
                                for (Item item : items) {
                                    instance.itemDao().insertAll(item);
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                Log.d("DEBUG0", e.getMessage());
                            }
                        });
            }
        }
        return instance;
    }
}
