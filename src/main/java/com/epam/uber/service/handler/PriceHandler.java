package com.epam.uber.service.handler;

import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.TariffServiceImpl;
import com.epam.uber.utils.PriceCalculator;

import java.time.LocalTime;

public class PriceHandler {
    private int tariffId;


    public double getCurrPrice(int from, int to) throws ServiceException {
        Tariff tariff = getTariff();
        double rate = tariff.getRate();
        return PriceCalculator.calculatePrice(from, to, rate);
    }

    private Tariff getTariff() throws ServiceException {
        TariffServiceImpl tariffService = new TariffServiceImpl();
        try {
            LocalTime currTime = LocalTime.now();
            setCurrTariffId(currTime.getHour());
            return tariffService.getById(tariffId);
        } finally {
            tariffService.endService();
        }
    }

    public int getTariffId() {
        return tariffId;
    }

    private void setCurrTariffId(int hour) {
        if ((hour > 7) && (hour <= 9)) {
            tariffId = 1;
        } else if ((hour > 9) && (hour <= 16)) {
            tariffId = 2;
        } else if ((hour > 16) && (hour <= 23)) {
            tariffId = 3;
        } else {
            tariffId = 4;
        }
    }
}
