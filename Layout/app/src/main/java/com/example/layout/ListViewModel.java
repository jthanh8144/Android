package com.example.layout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> histories;
    public LiveData<ArrayList<String>> getHistories() {
        if (histories == null) {
            histories = new MutableLiveData<ArrayList<String>>();
            histories.setValue(new ArrayList<String>());
        }
        return histories;
    }

    public void addHistory(String history) {
        ArrayList<String> arr = (ArrayList<String>) histories.getValue();
        arr.add(history);
        histories.setValue(arr);
    }
}
