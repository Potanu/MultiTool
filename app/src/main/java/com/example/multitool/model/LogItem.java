package com.example.multitool.model;

public class LogItem {
    private final String name;
    private final String updatedAt;

    public LogItem(String name, String updatedAt){
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public String getName(){
        return name;
    }

    public String getUpdatedAt() { return updatedAt; }

}
