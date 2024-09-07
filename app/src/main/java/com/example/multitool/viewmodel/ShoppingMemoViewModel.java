package com.example.multitool.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.multitool.data.dao.ChecklistItemDao;
import com.example.multitool.model.ChecklistItem;
import com.example.multitool.util.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // セーブボタンを押したときの処理
    public void saveChecklistItemPrompt(){
        String current_time = DataUtil.getCurrentDateTime();

        for (ChecklistItem item : checklistItems) {
            if (Objects.equals(item.getName(), "") || item.getIsChecked() == 0){
                continue;   // checkがなければスキップ
            }

            item.setUpdatedAt(current_time);

            if (item.getId() == -1){
                int id = checklistItemDao.insertData(
                        item.getName(),
                        item.getIsChecked(),
                        0,
                        current_time
                );

                item.setId(id);
            } else {
                checklistItemDao.updateItem(
                        item.getId(),
                        item.getName(),
                        item.getIsChecked(),
                        0,
                        current_time
                );
            }

            logItems.add(item);
        }
    }

    // アプリ終了時に呼ばれるセーブ処理
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

        removeChecklistItems.clear();

        // データの挿入・更新
        for (ChecklistItem item : checklistItems){
            if (!item.getIsChanged()){
                continue;   // 変更がなければスキップ
            }

            if (item.getId() == -1){
                int id = checklistItemDao.insertData(
                        item.getName(),
                        item.getIsChecked(),
                        0,
                        current_time
                );

                item.setId(id);
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

    public boolean isCanSave(){
        boolean isExistCheck = false;
        for (ChecklistItem item : checklistItems) {
            if (!Objects.equals(item.getName(), "") && item.getIsChecked() == 1){
                isExistCheck = true;
                break;
            }
        }

        return isExistCheck;
    }
}