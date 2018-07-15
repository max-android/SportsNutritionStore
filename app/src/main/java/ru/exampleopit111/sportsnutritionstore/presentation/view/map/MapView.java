package ru.exampleopit111.sportsnutritionstore.presentation.view.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import ru.exampleopit111.sportsnutritionstore.model.entities.common.MapEntity;

/**
 * Created Максим on 09.07.2018.
 * Copyright © Max
 */
public interface MapView {
    void showStores(List<MapEntity> stores);

    void showRoute(List<LatLng> locations);

    void showProgress();

    void removeProgress();

    void showError(String error);
}
