package com.kakura.libraryproject.entity;

public enum UserRole {

    ADMIN,
    LIBRARIAN,
    USER,
    GUEST;

    public String getRole() {
        return this.toString().toLowerCase();
    }

}
