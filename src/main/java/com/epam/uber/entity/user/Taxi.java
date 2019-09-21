package com.epam.uber.entity.user;

import java.io.Serializable;
import java.util.Objects;

public class Taxi extends Entity implements Serializable {
    private int locationId = -1;
    private boolean status;

    public Taxi(int id, int locationId, boolean status) {
        super(id);
        this.locationId = locationId;
        this.status = status;
    }


    public Taxi() {
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Taxi taxi = (Taxi) o;
        return locationId == taxi.locationId &&
                status == taxi.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), locationId, status);
    }

    @Override
    public String toString() {
        return "Taxi{" +
                ", locationId=" + locationId +
                ", state=" + status +
                '}';
    }
}
