package com.example.multitool.model;

import java.util.Objects;

public class ChecklistItem {
    private int id;
    private boolean isChecked;
    private String name;
    private String updatedAt;
    private final String defaultName;         // 名前変更判別用
    private final Boolean defaultIsChecked;   // チェックリスト状態変更判別用

    // ユーザーの操作で新たに生成
    public ChecklistItem() {
        this.id = -1;
        this.name = "";
        this.isChecked = false;
        this.updatedAt= "";
        defaultName = "";
        defaultIsChecked = false;
    }

    // dbの値から生成
    public ChecklistItem(int id, int isChecked, String name, String updatedAt) {
        this.id = id;
        this.isChecked = isChecked == 1;
        this.name = name;
        this.updatedAt = updatedAt;
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

    public String getUpdatedAt() { return updatedAt; }

    public void setId(int id) {this.id = id;}
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdatedAt(String updatedAt) {this.updatedAt = updatedAt;}
}
