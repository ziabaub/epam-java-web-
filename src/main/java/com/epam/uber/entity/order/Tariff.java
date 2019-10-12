package com.epam.uber.entity.order;

import com.epam.uber.entity.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tariff extends Entity implements Serializable {
    private LocalDateTime start;
    private double rate;

    public Tariff(int id, LocalDateTime start, double rate) {
        super(id);
        this.start = start;
        this.rate = rate;
    }

    public Tariff(double rate) {
        this.start = LocalDateTime.now();
        this.rate = rate;
    }

    public Tariff() {
    }

    public LocalDate getDate(){
        return start.toLocalDate();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tariff tariff = (Tariff) o;
        return Double.compare(tariff.rate, rate) == 0 &&
                Objects.equals(start, tariff.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, rate);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "start=" + start +
                ", rate=" + rate +
                '}';
    }
}
