package com.example.multitool.data.model;

public class ItemCategory {
    private int id;
    private String name;
    private int colorId;
    private int sortOrder;
    private String createdAt;
    private String updatedAt;

    public ItemCategory(){
    };

    public ItemCategory(int id, String name, int colorId, int sortOrder, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.colorId = colorId;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColorId() {
        return colorId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColorId(int colorId){
        this.colorId = colorId;
    }

    public void setSortOrder(int sortOrder){
        this.sortOrder = sortOrder;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }
}
