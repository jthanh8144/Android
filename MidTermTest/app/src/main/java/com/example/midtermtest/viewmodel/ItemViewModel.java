package com.example.midtermtest.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.midtermtest.model.Item;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemViewModel extends AndroidViewModel {
    private MutableLiveData<List<Item>> listItems;
    private ApiService apiService;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiService.getInstance(application);
    }

    private void setListItems() {
        apiService.getItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Item>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Item> items) {
                        listItems.setValue(items);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG0", e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<Item>> getListItems() {
        if (listItems == null) {
            listItems = new MutableLiveData<List<Item>>();
            setListItems();
        }
        return listItems;
    }
}
