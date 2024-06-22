package com.example.multitool.ui.Shopping_Memo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoppingMemoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShoppingMemoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is memo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}