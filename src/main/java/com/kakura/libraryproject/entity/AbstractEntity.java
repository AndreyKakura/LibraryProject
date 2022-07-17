package com.kakura.libraryproject.entity;

public abstract class AbstractEntity {
    private long id;

    public AbstractEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntity abstractEntity = (AbstractEntity) o;

        return id == abstractEntity.id;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result * 31 + Long.hashCode(id);
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("id = ")
                .append(id)
                .toString();
    }
}
