package com.epam.uber.utils;

import com.epam.uber.entity.client.OrderInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderFilter {
    private List<OrderInfo> orders;

    public OrderFilter(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public List<OrderInfo> getWaitingOrders(int zone) {
        List<OrderInfo> availableOrder = new ArrayList<>();
        for (OrderInfo o : orders) {
            boolean lessThenMinute = isWaiting(o.getDate());
            boolean isNear = isNear(zone, o.getClientZone());
            if (isNear) {
                availableOrder.add(o);
            }
            if (!lessThenMinute && !isNear) {
                availableOrder.add(o);
            }
        }
        return availableOrder;
    }


    private boolean isNear(int taxiZone, int clientZone) {
        int distanceBetween = Math.abs(taxiZone - clientZone);
        return (distanceBetween <= 6);
    }

    private boolean isWaiting(LocalDateTime madeItOrder) {
        int diff = LocalDateTime.now().getMinute() - madeItOrder.getMinute();
        return (diff <= 1);
    }
}
