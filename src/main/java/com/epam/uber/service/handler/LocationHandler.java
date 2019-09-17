package com.epam.uber.service.handler;

import com.epam.uber.entity.Location;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.utils.GPSManager;

import java.sql.Connection;

public class LocationHandler {
    private LocationServiceImpl locationService;

    public LocationHandler(Connection connection) {
        this.locationService = new LocationServiceImpl(connection);
    }

    public Location getCurrLocation() throws ServiceException {
        GPSManager gpsManager = new GPSManager();
        Location currentLocation = gpsManager.getCurrentLocation();
        int id = locationService.insert(currentLocation);
        currentLocation.setId(id);
        return currentLocation;
    }

    public Location getLocation(int zone) throws ServiceException {
        Location location = new Location(zone);
        int id = locationService.insert(location);
        location.setId(id);
        return location;

    }
}
