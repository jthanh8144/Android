package com.midterm.vovanthanh.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.midterm.vovanthanh.model.Item;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
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
                Log.d("DEBUGDB", "appdb no");
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
                                ItemViewModel.setListItems();
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
