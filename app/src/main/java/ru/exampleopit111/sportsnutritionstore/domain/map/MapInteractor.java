package ru.exampleopit111.sportsnutritionstore.domain.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.common.MapEntity;
import ru.exampleopit111.sportsnutritionstore.model.mapper.MapMapper;
import ru.exampleopit111.sportsnutritionstore.model.repository.MapRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.SportsNutritionRepository;

/**
 * Created Максим on 09.07.2018.
 * Copyright © Max
 */
public class MapInteractor {

    @Inject
    SportsNutritionRepository repository;

    @Inject
    MapRepository mapRepository;

    public MapInteractor(SportsNutritionRepository repository, MapRepository mapRepository) {
        this.repository = repository;
        this.mapRepository = mapRepository;
    }

    public Single<List<MapEntity>> locationStores() {
        return repository.locationStores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<LatLng>> route(String position, String destination,
                                      boolean sensor, String language, String key) {
        return mapRepository.route(position, destination, sensor, language, key)
                .map(MapMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
