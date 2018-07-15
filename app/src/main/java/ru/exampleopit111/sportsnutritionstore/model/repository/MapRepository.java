package ru.exampleopit111.sportsnutritionstore.model.repository;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.map.RouteResponse;
import ru.exampleopit111.sportsnutritionstore.model.network.MapService;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public class MapRepository {

    @Inject
    MapService mapService;

    public MapRepository(MapService mapService) {
        this.mapService = mapService;
    }

    public Single<RouteResponse> route(String position,String destination,
                                       boolean sensor,String language,String key){
      return mapService.route(position,destination,sensor,language,key);
    }
}
