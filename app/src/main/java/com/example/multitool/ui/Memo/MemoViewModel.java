package com.example.multitool.ui.Memo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MemoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MemoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is memo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}