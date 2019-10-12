package com.epam.uber.entity.order;

import com.epam.uber.entity.Entity;

import java.io.Serializable;
import java.util.Objects;

public class Location extends Entity implements Serializable {
    private String country;
    private String city;
    private int zone;


    public Location(int id, String country, String city, int zone) {
        super(id);
        this.country = country;
        this.city = city;
        this.zone = zone;
    }

    public Location(int zone) {
        this.country = "Belarus";
        this.city = "Minsk";
        this.zone = zone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return zone == location.zone &&
                Objects.equals(country, location.country) &&
                Objects.equals(city, location.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, zone);
    }

    @Override
    public String toString() {
        return "Location{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", zone=" + zone +
                '}';
    }
}