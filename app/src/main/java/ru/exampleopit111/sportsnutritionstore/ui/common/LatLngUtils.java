package ru.exampleopit111.sportsnutritionstore.ui.common;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public class LatLngUtils {
    public static String transform(LatLng location) {
        return String.valueOf(location).replace("lat/lng: (", "").replace(")", "");
    }
}
