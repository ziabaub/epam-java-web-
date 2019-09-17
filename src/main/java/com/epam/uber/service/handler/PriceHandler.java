package com.epam.uber.service.handler;

import com.epam.uber.entity.Tariff;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.TariffServiceImpl;
import com.epam.uber.utils.PriceCalculator;

import java.sql.Connection;
import java.time.LocalTime;

public class PriceHandler {
    private TariffServiceImpl tariffService;
    private int tariffId;

    public PriceHandler(Connection connection) {
        this.tariffService = new TariffServiceImpl(connection);
    }

    public double getCurrPrice(int from, int to) throws ServiceException {
        PriceCalculator calculator = new PriceCalculator();
        Tariff tariff = getTariff();
        double rate = tariff.getRate();
        return calculator.calculatePrice(from, to, rate);
    }

    private Tariff getTariff() throws ServiceException {
        LocalTime currTime = LocalTime.now();
        setCurrTariffId(currTime.getHour());
        return tariffService.getById(tariffId);
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
