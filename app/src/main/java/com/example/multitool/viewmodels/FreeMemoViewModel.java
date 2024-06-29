package com.example.multitool.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FreeMemoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FreeMemoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FreeMemo Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}