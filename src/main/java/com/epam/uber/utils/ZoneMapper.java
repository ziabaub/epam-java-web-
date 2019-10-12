package com.epam.uber.utils;


import java.util.*;

public class ZoneMapper {

    private ZoneMapper() {
    }

    private static final List<String> areas ;

    static {
        areas = Arrays.asList("Tashkenetot","Dolginovskiy","Kopische",
        "Factory","Odoevskogo ","Tomsoklo","Komarovskoye",
        " Tashkent","viaduct ","Logoiski","Tomsk","Tashkent","Dolginovskiy",
                "Kopische","Voronianskogo","Tashkent","viaduct","Logoiski",
                "Tomsk","Factory","Odoevskogo ","Voronianskogo");
    }

    public static String getArea(int  zone) {
        return  areas.get(zone);
    }

    public static int getZone (String area ){
        return  areas.lastIndexOf(area);
    }

}
