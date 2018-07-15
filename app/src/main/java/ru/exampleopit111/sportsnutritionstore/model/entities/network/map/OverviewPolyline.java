package ru.exampleopit111.sportsnutritionstore.model.entities.network.map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public class OverviewPolyline {
    @SerializedName("points")
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }
}
