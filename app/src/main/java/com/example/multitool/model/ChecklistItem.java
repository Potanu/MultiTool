package com.example.multitool.model;

public class ChecklistItem {
    private boolean isChecked;
    private String text;

    public ChecklistItem(boolean isChecked, String text) {
        this.isChecked = isChecked;
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getText(){
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
