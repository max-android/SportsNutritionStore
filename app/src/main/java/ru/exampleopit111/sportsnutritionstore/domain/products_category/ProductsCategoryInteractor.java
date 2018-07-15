package ru.exampleopit111.sportsnutritionstore.domain.products_category;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;
import ru.exampleopit111.sportsnutritionstore.model.repository.SportsNutritionRepository;

/**
 * Created Максим on 22.06.2018.
 * Copyright © Max
 */
public class ProductsCategoryInteractor {

    @Inject
    SportsNutritionRepository serviceRepository;

    @Inject
    DataBaseRepository dataBaseRepository;

    public ProductsCategoryInteractor(SportsNutritionRepository serviceRepository,
                                      DataBaseRepository dataBaseRepository) {
        this.serviceRepository = serviceRepository;
        this.dataBaseRepository = dataBaseRepository;
    }

    public Single<List<Product>> products() {
        return serviceRepository.products();
    }

    public Single<List<Product>> writeDataIntoDB() {
        Single<List<Product>> single = serviceRepository.products()
                .doOnSuccess(response -> {
                    dataBaseRepository.writeDataIntoDB(response);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return single;
    }

    public Single<List<Product>> searchProductsByCategory(String category) {
        return dataBaseRepository.searchProductsByCategoryFromDB(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Single<Integer> readSizeFromDB() {
        return dataBaseRepository.readSizeFromBD()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateFavorite(String name, boolean favorite) {
        dataBaseRepository.updateFavorite(name, favorite);
    }

    public void updateBasket(String name, boolean favorite) {
        dataBaseRepository.updateBasket(name, favorite);
    }
}
