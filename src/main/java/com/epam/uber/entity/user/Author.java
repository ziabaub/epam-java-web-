package com.epam.uber.entity.user;

import java.util.Objects;

public class Author extends Entity {
    private String code ;
    private boolean role ;


    public Author(String code, boolean role) {
        this.code = code;
        this.role = role;
    }

    public Author(int id, String code, boolean role) {
        super(id);
        this.code = code;
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Author that = (Author) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), code, role);
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "code='" + code + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
