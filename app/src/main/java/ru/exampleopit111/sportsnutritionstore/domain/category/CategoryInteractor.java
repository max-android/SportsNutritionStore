package ru.exampleopit111.sportsnutritionstore.domain.category;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.network.ProductCategory;
import ru.exampleopit111.sportsnutritionstore.model.repository.SportsNutritionRepository;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class CategoryInteractor {

    @Inject
    SportsNutritionRepository repository;

    public CategoryInteractor(SportsNutritionRepository repository) {
        this.repository = repository;
    }

    public Single<List<ProductCategory>> categories() {
        return repository.categories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
