package com.epam.uber.utils;

import com.epam.uber.entity.order.Tariff;

public class PriceCalculator {
    public double calculatePrice(int from, int to, double rate) {
        int zoneCount = Math.abs(from - to);
        double rate = tariff.getRate();
        return zoneCount * rate;
    }
}
