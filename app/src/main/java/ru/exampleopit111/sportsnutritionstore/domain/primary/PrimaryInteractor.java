package ru.exampleopit111.sportsnutritionstore.domain.primary;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.Brand;
import ru.exampleopit111.sportsnutritionstore.model.repository.AdvertisingRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.SportsNutritionRepository;

/**
 * Created Максим on 21.06.2018.
 * Copyright © Max
 */
public class PrimaryInteractor {

    @Inject
    AdvertisingRepository repository;

    @Inject
    SportsNutritionRepository sportsNutritionRepository;

    public PrimaryInteractor(AdvertisingRepository repository,
                             SportsNutritionRepository sportsNutritionRepository) {
        this.repository = repository;
        this.sportsNutritionRepository = sportsNutritionRepository;
    }

    public Flowable<String> getData() {
        return repository.getPrimaryData();
    }

    public Single<List<Brand>> getBrands() {
        return sportsNutritionRepository.brands()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
