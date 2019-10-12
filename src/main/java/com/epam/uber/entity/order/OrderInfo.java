package com.epam.uber.entity.order;

import com.epam.uber.entity.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderInfo extends Entity implements Serializable {
    private LocalDateTime date;
    private String currArea;
    private String destArea;
    private int cost ;

    public OrderInfo(int id, LocalDateTime date, String currArea, String destArea, int cost ) {
        super(id);
        this.date = date;
        this.currArea = currArea;
        this.destArea = destArea;
        this.cost = cost;
    }

    public OrderInfo(int id, String destArea, int cost) {
        super(id);
        this.destArea = destArea;
        this.cost = cost;
    }

    public String getDestArea() {
        return destArea;
    }

    public void setDestArea(String destArea) {
        this.destArea = destArea;
    }

    public LocalDateTime getDateTime() {
        return date;
    }

    public LocalDate getDate(){
        return date.toLocalDate();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCurrArea() {
        return currArea;
    }

    public void setCurrArea(String currArea) {
        this.currArea = currArea;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderInfo orderInfo = (OrderInfo) o;
        return currArea.equals(orderInfo.currArea) &&
                destArea.equals(orderInfo.destArea) &&
                cost == orderInfo.cost &&
                Objects.equals(date, orderInfo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, currArea, destArea, cost);
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "date=" + date +
                ", clientZone=" + currArea +
                ", destinationZone=" + destArea +
                ", cost=" + cost +
                '}';
    }
}
