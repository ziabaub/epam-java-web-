package com.epam.uber.utils;

import com.epam.uber.entity.order.Tariff;

public class PriceCalculator {

    private PriceCalculator() {
    }

    public static double calculatePrice(int from, int to, Tariff tariff) {
        int zoneCount = Math.abs(from - to);
        double rate = tariff.getRate();
        return zoneCount * rate;
    }
}
