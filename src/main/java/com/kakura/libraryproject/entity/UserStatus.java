package com.kakura.libraryproject.entity;

public enum UserStatus {

    ACTIVE,
    BLOCKED;

    public String getStatus() {
        return this.toString().toLowerCase();
    }

}
