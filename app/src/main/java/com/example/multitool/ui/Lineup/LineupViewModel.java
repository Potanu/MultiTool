package com.example.multitool.ui.Lineup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LineupViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LineupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Lineup fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}