package com.epam.uber.service.handler;

import com.epam.uber.entity.Location;
import com.epam.uber.exceptions.ServiceException;
import com.epam.uber.service.impl.LocationServiceImpl;
import com.epam.uber.utils.GPSManager;

public class LocationHandler {

    public Location getCurrLocation() throws ServiceException {
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            Location currentLocation = GPSManager.getCurrentLocation();
            int id = locationService.insert(currentLocation);
            currentLocation.setId(id);
            return currentLocation;
        } finally {
            locationService.endService();
        }
    }

    public Location getLocation(int zone) throws ServiceException {
        LocationServiceImpl locationService = new LocationServiceImpl();
        try {
            Location location = new Location(zone);
            int id = locationService.insert(location);
            location.setId(id);
            return location;
        } finally {
            locationService.endService();
        }

    }
}
