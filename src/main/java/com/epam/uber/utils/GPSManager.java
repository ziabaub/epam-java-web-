package com.epam.uber.utils;

import com.epam.uber.entity.order.Location;

import java.util.Random;

public class GPSManager {
    private GPSManager() {
    }

    public static Location getCurrentLocation (){
        Random rand = new Random();
        int zone = rand.nextInt(22);
        return new Location(zone);
    }
}
