package ru.exampleopit111.sportsnutritionstore.domain.brand;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exampleopit111.sportsnutritionstore.model.entities.database.Product;
import ru.exampleopit111.sportsnutritionstore.model.repository.DataBaseRepository;

/**
 * Created Максим on 11.07.2018.
 * Copyright © Max
 */
public class BrandInteractor {

    @Inject
    DataBaseRepository repository;

    public BrandInteractor(DataBaseRepository dataBaseRepository) {
        this.repository = dataBaseRepository;
    }

    public Single<List<Product>> searchProductsByBrand(String brand) {
        return repository.searchProductsByBrandFromDB(brand)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void updateFavorite(String name, boolean favorite) {
        repository.updateFavorite(name, favorite);
    }

    public void updateBasket(String name, boolean favorite) {
        repository.updateBasket(name, favorite);
    }
}
