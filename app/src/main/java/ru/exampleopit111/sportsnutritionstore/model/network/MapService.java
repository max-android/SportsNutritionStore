package ru.exampleopit111.sportsnutritionstore.model.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.map.MapApi;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.map.RouteResponse;

/**
 * Created Максим on 10.07.2018.
 * Copyright © Max
 */
public interface MapService {
    @GET(MapApi.REQUEST_URL)
    Single<RouteResponse> route(
            @Query(value = "origin") String position,
            @Query(value = "destination") String destination,
            @Query("sensor") boolean sensor,
            @Query("language") String language,
            @Query("key") String key);
}
