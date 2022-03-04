package com.example.layout;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

public class EditTextModel extends ViewModel {
    private MutableLiveData<String> text;
    public LiveData<String> getText() {
        if (text == null) {
            text = new MutableLiveData<String>();
            text.setValue("");
        }
        return text;
    }
}
