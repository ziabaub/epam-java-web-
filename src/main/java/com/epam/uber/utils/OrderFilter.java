package com.epam.uber.utils;

import com.epam.uber.entity.order.OrderInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderFilter {
    private final List<OrderInfo> orders;

    public OrderFilter(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public List<OrderInfo> getWaitingOrders(int zone) {
        List<OrderInfo> availableOrder = new ArrayList<>();
        int currZone;
        for (OrderInfo o : orders) {
            boolean lessThenMinute = isWaiting(o.getDateTime());
            currZone = ZoneMapper.getZone(o.getCurrArea());
            boolean isNear = isNear(zone, currZone);
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
