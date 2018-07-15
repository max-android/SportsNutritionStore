package ru.exampleopit111.sportsnutritionstore.model.entities.network.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public class Route {

    @SerializedName("overview_polyline")
    @Expose
    private OverviewPolyline overviewPolyline;

    @SerializedName("summary")
    @Expose
    private String summary;

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public String getSummary() {
        return summary;
    }
}
