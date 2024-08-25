package com.example.multitool.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.multitool.data.dao.ChecklistItemDao;
import com.example.multitool.model.ChecklistItem;
import com.example.multitool.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

public class ShoppingMemoViewModel extends ViewModel {
    public List<ChecklistItem> checklistItems;          // チェックリスト一覧のセル
    public List<ChecklistItem> removeChecklistItems;    // 削除したセルの一覧
    public List<ChecklistItem> logItems;                // ログ一覧
    private ChecklistItemDao checklistItemDao;

    public ShoppingMemoViewModel(){}

    public void Init(ChecklistItemDao checklistItemDao){
        this.checklistItemDao = checklistItemDao;
        removeChecklistItems = new ArrayList<>();
        loadChecklistItems();
        loadLogItems();
    }

    // 未チェックのデータリストを取得する
    private void loadChecklistItems(){
        // 未チェックかつ未削除のデータを取得する
        String selection = String.format("%s = ? AND %s = ?",
                checklistItemDao.COLUMN_IS_CHECK,
                checklistItemDao.COLUMN_IS_DELETE);
        String[] selectionArgs = {"0", "0"};

        checklistItems = checklistItemDao.getData(null, selection, selectionArgs);
    }

    // チェック済みのデータリストを取得する
    private void loadLogItems(){
        // チェック済みかつ未削除のデータを取得する
        String selection = String.format("%s = ? AND %s = ?",
                checklistItemDao.COLUMN_IS_CHECK,
                checklistItemDao.COLUMN_IS_DELETE);
        String[] selectionArgs = {"1", "0"};

        logItems = checklistItemDao.getData(null, selection, selectionArgs);
    }

    public void saveChecklistItem(){
        String current_time = DataUtil.getCurrentDateTime();

        // 削除対象のデータを更新
        for (ChecklistItem item : removeChecklistItems){
            checklistItemDao.updateItem(
                    item.getId(),
                    item.getName(),
                    item.getIsChecked(),
                    1,
                    current_time
            );
        }

        // データの挿入・更新
        for (ChecklistItem item : checklistItems){
            if (!item.getIsChanged()){
                continue;   // 変更がなければスキップ
            }

            if (item.getId() == -1){
                checklistItemDao.insertData(
                        item.getName(),
                        item.getIsChecked(),
                        0,
                        current_time
                );
            } else {
                checklistItemDao.updateItem(
                        item.getId(),
                        item.getName(),
                        item.getIsChecked(),
                        0,
                        current_time
                );
            }
        }
    }
}