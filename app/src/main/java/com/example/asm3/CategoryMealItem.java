package com.example.asm3;

public class CategoryMealItem {
    private String ID;
    private String name;
    private String imageUrl;

    public CategoryMealItem(String ID, String name, String imageUrl) {
        this.ID = ID;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
