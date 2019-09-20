package com.epam.uber.utils;

public class PriceCalculator {
    public double calculatePrice(int from, int to, double rate) {
        int zoneCount = Math.abs(from - to);
        double rate = tariff.getRate();
        return zoneCount * rate;
    }
}
