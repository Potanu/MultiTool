package com.example.multitool.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multitool.data.dao.ChecklistItemDao;
import com.example.multitool.databinding.FragmentChecklistBinding;
import com.example.multitool.util.DataUtil;
import com.example.multitool.viewmodel.ShoppingMemoViewModel;
import com.example.multitool.ui.ChecklistAdapter;
import com.example.multitool.R;

import java.util.ArrayList;

public class ChecklistFragment extends Fragment {
    private ShoppingMemoViewModel shoppingMemoViewModel;
    private FragmentChecklistBinding binding;
    private ChecklistAdapter checklistAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingMemoViewModel = new ViewModelProvider(requireActivity()).get(ShoppingMemoViewModel.class);
        ChecklistItemDao checklistItemDao = new ChecklistItemDao(requireActivity());
        shoppingMemoViewModel.Init(checklistItemDao);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChecklistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checklistAdapter = new ChecklistAdapter(new ArrayList<>());
        recyclerView.setAdapter(checklistAdapter);

        checklistAdapter.setChecklistItems(shoppingMemoViewModel.checklistItems);
        checklistAdapter.setRemoveChecklistItems(shoppingMemoViewModel.removeChecklistItems);

        // 追加ボタン押下時の処理を登録
        binding.addButton.setOnClickListener(v -> {
            String currentTime = DataUtil.getCurrentDateTime();
            checklistAdapter.addItem(currentTime);
        });

        // セーブボタン押下時の処理を登録
        binding.saveButton.setOnClickListener(v ->{
            if (!shoppingMemoViewModel.isCanSave()){
                return;
            }

            shoppingMemoViewModel.saveChecklistItemPrompt();
            checklistAdapter.removeItem();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}