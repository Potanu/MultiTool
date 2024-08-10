package com.example.multitool.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.multitool.model.ChecklistItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingMemoViewModel extends ViewModel {

    private final MutableLiveData<List<ChecklistItem>> checklistItems;

    public ShoppingMemoViewModel() {
        checklistItems = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<ChecklistItem>> getChecklistItems() {
        return checklistItems;
    }
}