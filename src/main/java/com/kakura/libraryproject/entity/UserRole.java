package com.kakura.libraryproject.entity;

public enum UserRole {

    ADMIN,
    USER,
    GUEST;

    public String getRole() {
        return this.toString().toLowerCase();
    }

}
