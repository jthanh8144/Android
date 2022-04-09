package com.example.midtermtest.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.midtermtest.model.Item;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemViewModel extends AndroidViewModel {
    private static MutableLiveData<List<Item>> listItems;
    private ApiService apiService;

    private static ItemDao itemDao;
    private static AppDatabase appDatabase;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiService.getInstance(application);

        appDatabase = AppDatabase.getInstance(application);
        itemDao = appDatabase.itemDao();
    }

    public static void setListItems() {
        List<Item> data = itemDao.getAll();
        listItems.setValue(data);

//        apiService.getItems()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Item>>() {
//                    @Override
//                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Item> items) {
//                        listItems.setValue(items);
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                        Log.d("DEBUG0", e.getMessage());
//                    }
//                });
    }

    public MutableLiveData<List<Item>> getListItems() {
        if (listItems == null) {
            listItems = new MutableLiveData<List<Item>>();
            setListItems();
        }
        return listItems;
    }
}
