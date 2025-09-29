package com.businessbrasil.api.api_cadastro_usuarios.service.dto;

public class UserSearchRequest {

    private String name;
    private String email;
    private Boolean isActive;
    private int page = 0;
    private int size = 10;
    private String sortBy = "name";
    private String sortDirection = "asc";

    public UserSearchRequest() {
    }

    public UserSearchRequest(String name, String email, Boolean isActive, int page, int size, String sortBy,
            String sortDirection) {
        this.name = name;
        this.email = email;
        this.isActive = isActive;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
