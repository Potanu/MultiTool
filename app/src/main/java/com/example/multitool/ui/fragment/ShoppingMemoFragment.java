package com.example.multitool.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multitool.MainActivity;
import com.example.multitool.data.dao.ItemCategoryDao;
import com.example.multitool.data.model.ItemCategory;
import com.example.multitool.databinding.FragmentShoppingMemoBinding;
import com.example.multitool.model.ChecklistItem;
import com.example.multitool.viewmodel.ShoppingMemoViewModel;
import com.example.multitool.ui.ChecklistAdapter;
import com.example.multitool.R;

import java.util.List;
import java.util.ArrayList;

public class ShoppingMemoFragment extends Fragment {
    private ItemCategoryDao itemCategoryDao;
    private FragmentShoppingMemoBinding binding;
    private ChecklistAdapter checklistAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) requireActivity();
        itemCategoryDao = mainActivity.getItemCategoryDao();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingMemoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        checklistAdapter = new ChecklistAdapter(new ArrayList<>());
        recyclerView.setAdapter(checklistAdapter);

        ShoppingMemoViewModel shoppingMemoViewModel = new ViewModelProvider(requireActivity()).get(ShoppingMemoViewModel.class);
        shoppingMemoViewModel.getChecklistItems().observe(getViewLifecycleOwner(), checklistItems -> {
            checklistAdapter.updateChecklistItems(checklistItems);
        });

        // +ボタン押下時の処理を登録
        binding.addButton.setOnClickListener(v -> {
            checklistAdapter.addItem();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}