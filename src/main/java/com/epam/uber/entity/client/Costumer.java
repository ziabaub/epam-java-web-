package com.epam.uber.entity.client;

import com.epam.uber.entity.user.Entity;

import java.util.Objects;

public class Costumer extends Entity {
    private String name;
    private String phone;
    private String email;
    private String note;

    public Costumer(int id,String name, String phone, String note,String email) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
    }

    public Costumer(String name, String phone, String email, String note) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
    }

    public Costumer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Costumer costumer = (Costumer) o;
        return Objects.equals(name, costumer.name) &&
                Objects.equals(phone, costumer.phone) &&
                Objects.equals(email, costumer.email) &&
                Objects.equals(note, costumer.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, phone, email, note);
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
