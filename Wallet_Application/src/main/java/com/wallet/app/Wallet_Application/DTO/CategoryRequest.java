package com.wallet.app.Wallet_Application.DTO;

public class CategoryRequest {
    private Long id;
    private String name;

    public CategoryRequest() {
    }

    public CategoryRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
