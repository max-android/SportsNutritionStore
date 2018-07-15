package ru.exampleopit111.sportsnutritionstore.model.mapper;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.network.map.RouteResponse;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public class MapMapper {
    public static List<LatLng> transform(RouteResponse response) {
        String points = response.getRoutes().get(0).getOverviewPolyline().getPoints();
        List<LatLng> lngList = new ArrayList<>(PolyUtil.decode(points));
        return lngList;
    }
}
