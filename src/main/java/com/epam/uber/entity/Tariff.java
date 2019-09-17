package com.epam.uber.entity;

import com.epam.uber.entity.user.Entity;

import java.time.LocalTime;
import java.util.Objects;

public class Tariff extends Entity {
    private LocalTime start ;
    private LocalTime end ;
    private double rate ;

    public Tariff(int id, LocalTime start, LocalTime end, double rate) {
        super(id);
        this.start = start;
        this.end = end;
        this.rate = rate;
    }

    public Tariff(LocalTime start, LocalTime end, double rate) {
        this.start = start;
        this.end = end;
        this.rate = rate;
    }

    public Tariff() {
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
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
                Objects.equals(start, tariff.start) &&
                Objects.equals(end, tariff.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), start, end, rate);
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "start=" + start +
                ", end=" + end +
                ", rate=" + rate +
                '}';
    }
}
