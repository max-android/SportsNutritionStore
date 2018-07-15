package ru.exampleopit111.sportsnutritionstore.model.entities.network.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public class RouteResponse {
    @SerializedName("routes")
    @Expose
    public List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }
}
