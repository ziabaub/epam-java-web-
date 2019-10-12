package com.epam.uber.entity.user;

import com.epam.uber.entity.Entity;

import java.io.Serializable;
import java.util.Objects;

public class UserTaxi extends Entity implements Serializable {

    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private int location;

    public UserTaxi(int id, String firstName, String lastName, String login, String email, int location) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserTaxi userTaxi = (UserTaxi) o;
        return location == userTaxi.location &&
                Objects.equals(firstName, userTaxi.firstName) &&
                Objects.equals(lastName, userTaxi.lastName) &&
                Objects.equals(login, userTaxi.login) &&
                Objects.equals(email, userTaxi.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, login, email, location);
    }

    @Override
    public String toString() {
        return "UserTaxi{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", location=" + location +
                '}';
    }
}
