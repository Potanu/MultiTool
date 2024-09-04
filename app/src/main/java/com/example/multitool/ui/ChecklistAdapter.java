package com.example.multitool.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multitool.R;
import com.example.multitool.model.ChecklistItem;

import java.util.List;
import java.util.Objects;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private List<ChecklistItem> checklistItems;
    private List<ChecklistItem> removeChecklistItems;
    private Runnable onChangeCheckboxAction;

    public ChecklistAdapter(List<ChecklistItem> checklistItems) {
        this.checklistItems = checklistItems;
    }

    // セルが更新されたときの処理を登録する
    public void setOnChangeDataAction(Runnable action){
        onChangeCheckboxAction = action;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChecklistItem item = checklistItems.get(position);

        // チェックボックスの設定
        holder.checkBox.setChecked(item.getIsChecked() == 1);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // データモデルを更新する
            checklistItems.get(holder.getAbsoluteAdapterPosition()).setChecked(isChecked);

            if (onChangeCheckboxAction != null){
                onChangeCheckboxAction.run();
            }
        });

        // 削除ボタンの設定
        holder.removeButton.setOnClickListener(v -> {
            removeItem(position);
            onChangeCheckboxAction.run();
        });

        // EditTextの設定
        holder.editText.setText(item.getName());
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // データモデルを更新する
                checklistItems.get(holder.getAbsoluteAdapterPosition()).setName(s.toString());
                onChangeCheckboxAction.run();
            }
        });
    }

    @Override
    public int getItemCount() {
        return checklistItems.size();
    }

    // ShoppingMemoViewModel のchecklistItemsと紐づける
    public void setChecklistItems(List<ChecklistItem> newItems) {
        this.checklistItems = newItems;
        notifyItemRangeChanged(0, checklistItems.size());
    }

    // ShoppingMemoViewModel のremoveItemIdsと紐づける
    public void setRemoveChecklistItems(List<ChecklistItem> removeItemIds){
        this.removeChecklistItems = removeItemIds;
    }

    public void addItem(String createdAt){
        ChecklistItem item = new ChecklistItem();
        checklistItems.add(item);
        notifyItemInserted(checklistItems.size() - 1);
    }

    public void removeItem(){
        for(int j = checklistItems.size() - 1; j >= 0; j--){
            if (!Objects.equals(checklistItems.get(j).getName(), "") &&
                    checklistItems.get(j).getIsChecked() == 1){
                checklistItems.remove(j);
                notifyItemRemoved(j);
            }
        }

        notifyItemRangeChanged(0, checklistItems.size());
    }

    private void removeItem(int position) {
        if (position < 0 || position >= checklistItems.size()) {
            return;
        }

        int id = checklistItems.get(position).getId();
        if (id > -1){
            removeChecklistItems.add(checklistItems.get(position));
        }

        checklistItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, checklistItems.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public ImageButton removeButton;
        public EditText editText;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            removeButton = itemView.findViewById(R.id.removeButton);
            editText = itemView.findViewById(R.id.editText);
        }
    }
}
