package com.example.multitool.ui.Shopping_Memo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multitool.databinding.FragmentShoppingMemoBinding;

public class ShoppingMemoFragment extends Fragment {

    private FragmentShoppingMemoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShoppingMemoViewModel shoppingMemoViewModel =
                new ViewModelProvider(this).get(ShoppingMemoViewModel.class);

        binding = FragmentShoppingMemoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}