package com.epam.uber.entity.client;

import com.epam.uber.entity.user.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class OrderInfo extends Entity implements Serializable {
    private Date date;
    private int clientZone;
    private int destinationZone;
    private int cost ;

    public OrderInfo(int id, Date date, int clientZone , int destinationZone,int cost ) {
        super(id);
        this.date = date;
        this.clientZone = clientZone;
        this.destinationZone = destinationZone;
        this.cost = cost;
    }

    public OrderInfo(int id, int destinationZone, int cost) {
        super(id);
        this.destinationZone = destinationZone;
        this.cost = cost;
    }

    public int getDestinationZone() {
        return destinationZone;
    }

    public void setDestinationZone(int destinationZone) {
        this.destinationZone = destinationZone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getClientZone() {
        return clientZone;
    }

    public void setClientZone(int clientZone) {
        this.clientZone = clientZone;
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
        return clientZone == orderInfo.clientZone &&
                destinationZone == orderInfo.destinationZone &&
                cost == orderInfo.cost &&
                Objects.equals(date, orderInfo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, clientZone, destinationZone, cost);
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "date=" + date +
                ", clientZone=" + clientZone +
                ", destinationZone=" + destinationZone +
                ", cost=" + cost +
                '}';
    }
}
