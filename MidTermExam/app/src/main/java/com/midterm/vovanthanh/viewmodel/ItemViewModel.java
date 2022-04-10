package com.midterm.vovanthanh.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.midterm.vovanthanh.model.Item;

import java.util.List;

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
    }

    public MutableLiveData<List<Item>> getListItems() {
        if (listItems == null) {
            listItems = new MutableLiveData<List<Item>>();
            setListItems();
        }
        return listItems;
    }
}
