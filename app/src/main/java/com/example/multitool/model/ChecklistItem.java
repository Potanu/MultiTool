package com.example.multitool.model;

import java.util.Objects;

public class ChecklistItem {
    private final int id;
    private boolean isChecked;
    private String name;
    private String updatedAt;
    private String defaultName;         // 名前変更判別用
    private Boolean defaultIsChecked;   // チェックリスト状態変更判別用

    // ユーザーの操作で新たに生成
    public ChecklistItem() {
        this.id = -1;
        this.name = "";
        this.isChecked = false;
        defaultName = "";
        defaultIsChecked = false;
    }

    // dbの値から生成
    public ChecklistItem(int id, int isChecked, String name) {
        this.id = id;
        this.isChecked = isChecked == 1;
        this.name = name;
        defaultName = this.name;
        defaultIsChecked = this.isChecked;
    }

    public int getId() { return id; }

    public Integer getIsChecked() {
        return isChecked ? 1 : 0;
    }

    public String getName(){
        return name;
    }

    public Boolean getIsChanged(){
        if (Objects.equals(name, defaultName) && isChecked == defaultIsChecked){
            // 名前もチェック状態もどちらも変化ない
            return false;
        }

        // 更新あり
        return true;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setName(String name) {
        this.name = name;
    }
}
