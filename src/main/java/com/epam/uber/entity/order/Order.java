package com.epam.uber.entity.order;

import com.epam.uber.entity.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class Order extends Entity implements Serializable {

    private Date date ;
    private int taxiId;
    private int costumerId;
    private int tariffId;
    private int currLocationId;
    private int destinationLocationId;
    private double cost;
    private String status;
    private int rate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(int taxiId) {
        this.taxiId = taxiId;
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public int getCurrLocationId() {
        return currLocationId;
    }

    public void setCurrLocationId(int currLocationId) {
        this.currLocationId = currLocationId;
    }

    public int getDestinationLocationId() {
        return destinationLocationId;
    }

    public void setDestinationLocationId(int destinationLocationId) {
        this.destinationLocationId = destinationLocationId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return taxiId == order.taxiId &&
                costumerId == order.costumerId &&
                tariffId == order.tariffId &&
                currLocationId == order.currLocationId &&
                destinationLocationId == order.destinationLocationId &&
                Double.compare(order.cost, cost) == 0 &&
                rate == order.rate &&
                Objects.equals(date, order.date) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, taxiId, costumerId, tariffId, currLocationId, destinationLocationId, cost, status, rate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", taxiId=" + taxiId +
                ", costumerId=" + costumerId +
                ", tariffId=" + tariffId +
                ", currLocationId=" + currLocationId +
                ", destinationLocationId=" + destinationLocationId +
                ", cost=" + cost +
                ", status=" + status +
                ", rate=" + rate +
                '}';
    }
}
