package com.example.multitool.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multitool.MainActivity;
import com.example.multitool.data.dao.ItemCategoryDao;
import com.example.multitool.data.model.ItemCategory;
import com.example.multitool.databinding.FragmentShoppingMemoBinding;
import com.example.multitool.viewmodels.ShoppingMemoViewModel;

import java.util.List;

public class ShoppingMemoFragment extends Fragment {
    private ItemCategoryDao itemCategoryDao;
    private FragmentShoppingMemoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) requireActivity();
        itemCategoryDao = mainActivity.getItemCategoryDao();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShoppingMemoViewModel shoppingMemoViewModel =
                new ViewModelProvider(this).get(ShoppingMemoViewModel.class);

        List<ItemCategory> list = itemCategoryDao.getAllData();
        binding = FragmentShoppingMemoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}