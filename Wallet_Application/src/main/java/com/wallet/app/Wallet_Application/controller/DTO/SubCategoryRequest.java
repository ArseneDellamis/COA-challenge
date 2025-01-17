package com.wallet.app.Wallet_Application.controller.DTO;

public class SubCategoryRequest {
    private long id;
    private String name;
    private long categoryId;

    public SubCategoryRequest(long id, String name, long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
