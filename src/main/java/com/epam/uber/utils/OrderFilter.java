package com.epam.uber.utils;

import com.epam.uber.entity.client.OrderInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFilter {
    private List<OrderInfo> orders;

    public OrderFilter(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public List<OrderInfo> getWaitingOrders(int zone) {
        Date date = new Date();
        List<OrderInfo> availableOrder = new ArrayList<>();
        for (OrderInfo o : orders) {
            boolean lessThenMinute = waitingLessThenMinute(date, o.getDate());
            boolean isNear = isNear(zone , o.getClientZone());
            if (isNear) {
                availableOrder.add(o);
            }
            if(!lessThenMinute && !isNear){
                availableOrder.add(o);
            }
        }
        return availableOrder;
    }


    private boolean isNear(int taxiZone, int clientZone) {
        int distanceBetween = Math.abs(taxiZone - clientZone);

        return (distanceBetween <= 6);
    }

    private boolean waitingLessThenMinute(Date curr, Date waiting) {
        long diff = curr.getTime() - waiting.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;

        return (diffMinutes <= 1);
    }
}
